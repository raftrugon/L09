package controllers.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.RequestService;
import services.UserService;
import services.ZerviceService;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.PaymentMethod;
import com.braintreegateway.PaymentMethodRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.CreditCard;
import domain.Rendezvous;
import domain.Request;
import domain.Zervice;


@RestController
@RequestMapping("/user/request")
public class UserRequestController {

	@Autowired
	private RequestService requestService;
	@Autowired
	private UserService userService;
	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private ZerviceService zerviceService;

	@Value("${BT_ENVIRONMENT}")
	private String environment;

	@Value("${BT_MERCHANT_ID}")
	private String merchantId;

	@Value("${BT_PUBLIC_KEY}")
	private String publicKey;

	@Value("${BT_PRIVATE_KEY}")
	private String privateKey;

	private BraintreeGateway gateway = new BraintreeGateway(environment,merchantId,publicKey,privateKey);

	public UserRequestController() {
		super();
	}

	@RequestMapping(value="/rendezvouses", method = RequestMethod.GET)
	public @ResponseBody String rendezvouses(@RequestParam(required = true) final Integer zerviceId){
		JsonArray json = new JsonArray();
		try{
			List<Rendezvous> list =  new ArrayList<Rendezvous>(requestService.selectRequestableRendezvousesForService(zerviceService.findOne(zerviceId)));
			for(Rendezvous r: list){
				JsonObject obj = new JsonObject();
				obj.addProperty("id", r.getId());
				obj.addProperty("name",r.getName());
				json.add(obj);
			}
			return json.toString();
		}catch(Throwable oops){
			oops.printStackTrace();
			return "";
		}

	}

	@RequestMapping(value="/zervices", method = RequestMethod.GET)
	public @ResponseBody String zervices(@RequestParam(required = true) final Integer rendezvousId){
		JsonArray json = new JsonArray();
		try{
			List<Zervice> list =  new ArrayList<Zervice>(requestService.selectRequestableServicesForRendezvous(rendezvousService.findOne(rendezvousId)));
			for(Zervice z: list){
				JsonObject obj = new JsonObject();
				obj.addProperty("id", z.getId());
				obj.addProperty("name",z.getName());
				json.add(obj);
			}
			return json.toString();
		}catch(Throwable oops){
			oops.printStackTrace();
			return "";
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(
			@RequestParam(required = false) final Integer rendezvousId,
			@RequestParam(required = false) final Integer zerviceId) {

		String clientToken = gateway.clientToken().generate();
		try {
			return newEditModelAndView(requestService.create(),rendezvousId, zerviceId).addObject("clientToken", clientToken);
		} catch (Throwable oops) {
			return new ModelAndView("ajaxException");
		}
	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(final Request request, final BindingResult binding, final String nonce){
		Request result = requestService.reconstruct(request,binding);
		if(binding.hasErrors()) return "0";
		else
			try{
				requestService.save(result);
				CreditCard cc = request.getCreditCard();
				PaymentMethodRequest paymentMethod = new PaymentMethodRequest()
				.cardholderName(cc.getHolderName())
				.number(cc.getNumber())
				.cvv(cc.getCvvCode().toString())
				.expirationMonth(cc.getExpirationMonth().toString())
				.expirationYear(cc.getExpirationYear().toString())
				.paymentMethodNonce(nonce);
				Result<PaymentMethod> result = gateway.paymentMethod().create(paymentMethod);
				return "1";
			}catch (Throwable oops){
				return "2";
			}
	}

	protected ModelAndView newEditModelAndView(Request request) {
		ModelAndView result = newEditModelAndView(request,null,null);
		return result;
	}

	protected ModelAndView newEditModelAndView(Request request,Integer rendezvousId,Integer zerviceId){
		ModelAndView result = new ModelAndView("request/create");
		result.addObject("request",request);
		if (rendezvousId != null) {
			result.addObject("zervices", requestService.selectRequestableServicesForRendezvous(rendezvousService.findOne(rendezvousId)));
			result.addObject("selectedRendezvous",rendezvousService.findOne(rendezvousId));
		} else
			result.addObject("zervices", zerviceService.findAllNotInappropriate());
		if (zerviceId != null) {
			result.addObject("rendezvouses",requestService.selectRequestableRendezvousesForService(zerviceService.findOne(zerviceId)));
			result.addObject("selectedZervice", zerviceService.findOne(zerviceId));
		} else
			result.addObject("rendezvouses",userService.getRequestableRendezvouses());
		return result;
	}

}
