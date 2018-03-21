package controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import services.ZerviceService;
import domain.Zervice;
 
public class ControllerTest {
	
	//Primero creamos el mock del servicio que usa el controlador que testeamos
    @Mock ZerviceService zerviceService;
 
    @Before
    public void setup()
    {
        //Inicializamos el mock antes de ejecutar el test
        MockitoAnnotations.initMocks( this );
    }
     
    @Test
    public void testListQuestions()
    {
        // Creamos una coleccion de servicios para simular el funcionamiento
    	// del método findAll()
        List<Zervice> zervices = new ArrayList<Zervice>();
        zervices.add( new Zervice());
        when( zerviceService.findAll()).thenReturn( zervices );
 
        // Creamos una instancia del controlador que vamos a testear
        ZerviceController controller = new ZerviceController();
 
        // Ajustamos los parámetros del controlador manualmente
        ReflectionTestUtils.setField( controller, "zerviceService", zerviceService );
 
        // Llamamos al método que queremos testear
        ModelAndView mav = controller.list();
 
        // Comprobamos que dicho método devuelve el resultado esperado
        assertEquals( zervices, mav.getModel().get( "zervices" ));
        // Comprobamos que el nombre de la vista que se genera es el esperado
        assertEquals( "zervice/list", mav.getViewName());
    }
}