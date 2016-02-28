package cl.chileindica.qa;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;

public class PreinversionTest {

	private static ChromeDriverService service;
	private WebDriver driver;

	@BeforeClass
	public static void createAndStartService() {
		service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("/home/mnunez/bin/chromedriver"))
				.usingAnyFreePort().build();
		try {
			service.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


	
	 @AfterClass
	    public static void createAndStopService() {
	      service.stop();
	    }
	 
	 @Before
	    public void createDriver() {
	      driver = new RemoteWebDriver(service.getUrl(),
	          DesiredCapabilities.chrome());
	    }
	 
	 @After
	    public void quitDriver() {
	      driver.quit();
	    }	
	

	@Test
	public void ingresarPreinversion() {

		String base = "http://172.17.0.1/";
		String system = "arica";
		String url = base + system;
		String usuario = "calvarez";
		String password = "calvarez123";

		ConfirmHandler cHandler = new ConfirmHandler() {

			@Override
			public boolean handleConfirm(Page page, String message) {
				System.out.println("Confirm:" + message);
				if (message.contains("nueva Iniciativa"))
					return true;
				return false;
			}
		};
		// WebDriver driver = new CustomHtmlUnitDriver(alerts,cHandler);
		// WebDriver driver=new PhantomJSDriver();
		//WebDriver driver = new ChromeDriver();

		driver.get(url);
		driver.findElement(By.name("e_usuario")).sendKeys(usuario);
		driver.findElement(By.name("e_clave")).sendKeys(password);
		driver.findElement(By.name("b_acceso")).click();

		driver.findElement(By.id("imenu1")).click();
		driver.findElement(By.id("imenu1-4")).click();

		driver.switchTo().frame("mpagina");

		Select institucion = new Select(driver.findElement(By
				.id("cb_institucion")));
		institucion.selectByIndex(1);

		driver.findElement(By.name("b_nueva")).click();
		
		

		//driver.switchTo().alert().dismiss();
		
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
		

		
		waitFor(5);
		
		System.out.println(driver.switchTo().alert().getText());
		//driver.switchTo().frame("mpagina");
		
		/*
		 * 
		 * - Para Dejar PENDIENTE, debe completar los siguientes datos para
		 * poder guardar:
		 * 
		 * - Debe Ingresar la Dirección de la Iniciativa! (al menos 10
		 * caracteres) - Debe Seleccionar el Sector de la Inversión! - Debe
		 * ingresar el Código de la Iniciativa - Sector de la Inversión - Fecha
		 * de Inicio de la Iniciativa - Fecha de Término de la Iniciativa - Debe
		 * Seleccionar la Via de Financiamiento! - Cuantificación de las
		 * Unidades Físicas (Máximo 140 caracteres) - Descripción de la
		 * Iniciativa (al menos 20 caracteres) - Falta ingresar Costo Total de
		 * la Iniciativa - Falta Ingresar Valor Solicitado Año de la Iniciativa
		 * - Debe Seleccionar el Área de Influencia de la Iniciativa - Debe
		 * relacionar la iniciativa con al menos un Instrumento/Política! - Debe
		 * adjuntar al menos un archivo y asignar un nombre 0 estado - Debe
		 * adjuntar archivos con formato compatible. Ver Manual de Archivos
		 * Permitidos
		 */
		
		driver.switchTo().alert().dismiss();

		driver.findElement(By.id("e_nombre")).sendKeys("Iniciativa Generada");

		driver.findElement(By.id("e_unidad_tecnica")).sendKeys("");

		driver.findElement(By.name("fecha_inicio")).sendKeys("31/08/2015");
		driver.findElement(By.name("fecha_termino")).sendKeys("31/08/2015");
		driver.findElement(By.name("e_producto")).sendKeys("E Producto");
		driver.findElement(By.name("e_codigo")).sendKeys("20150831001");
		// driver.findElement(By.name("e_impactos")).sendKeys("");
		driver.findElement(By.name("cb_sectorinversion")).sendKeys("");
		driver.findElement(By.name("Observaciones")).sendKeys(
				"Observaciones al proyecto");

		driver.findElement(By.name("texto_beneficiarios")).sendKeys(
				"Beneficiarios");
		// driver.findElement(By.name("c_clasificador_presupuestario")).sendKeys("");

		driver.findElement(By.name("e_costo_total")).sendKeys("100000");
		driver.findElement(By.name("e_solicitado")).sendKeys("100000");
		driver.findElement(By.name("e_saldo")).sendKeys("");
		driver.findElement(By.name("cb_etapa")).sendKeys("");

		driver.findElement(By.name("cb_via_financiamiento")).sendKeys("");
		driver.findElement(By.name("e_tipo")).sendKeys("");
		driver.findElement(By.name("cb_rate")).sendKeys("");
		driver.findElement(By.name("direccion")).sendKeys("");
		driver.findElement(By.name("justificacion_proyecto")).sendKeys("");
		// driver.findElement(By.name("descriptor")).sendKeys("");
		driver.findElement(By.name("cb_fuentef")).sendKeys("");
		driver.findElement(By.name("nombre_formulador")).sendKeys("");
		driver.findElement(By.name("telefono")).sendKeys("");
		driver.findElement(By.name("correo_electronico")).sendKeys("");

		driver.findElement(By.name("tir")).sendKeys("");
		driver.findElement(By.name("van")).sendKeys("");
		driver.findElement(By.name("cae")).sendKeys("");
		driver.findElement(By.name("fecha_rate")).sendKeys("");

		driver.findElement(By.name("b_pendiente")).click();
		
		
		waitFor(2);
		System.out.println(driver.switchTo().alert().getText());
		

		/*
		 * Select evaluador=new
		 * Select(driver.findElement(By.id("id_evaluador")));
		 * evaluador.selectByVisibleText("Todos");
		 */

	}





	private void waitFor(int i) {
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
