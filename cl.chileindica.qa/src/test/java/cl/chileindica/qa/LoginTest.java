package cl.chileindica.qa;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class LoginTest {

	static class Event {

	}

	@Test
	public void login() {
		String base = "http://172.17.0.1/";

		String[] systems = new String[] { "arica", "tarapaca", "antofagasta",
				"atacama", "coquimbo", "valparaiso", "rms", "ohiggins",
				"maule", "biobio", "araucania", "losrios", "loslagos", "aysen",
				"magallanes" };
		systems = new String[] { "araucania"};
		for (String system : systems) {
			assertEquals("Página de reporte inicial esperada", base + system
					+ "/inversiones/paginas/pagina1_3_1.php",
					check(base + system, "calvarez", "calvarez123").get(0));
			assertEquals("Página de preinversion esperada", base + system
					+ "/inversiones/paginas/pagina1_1_4.php",
					check(base + system, "calvarez", "calvarez123").get(1));
		}
	}

	private List<String> check(String url, String usuario, String password) {

		List<String> l = new LinkedList<String>();

		WebDriver driver = new HtmlUnitDriver(true);
		
		driver.get(url);
		driver.findElement(By.name("e_usuario")).sendKeys(usuario);
		driver.findElement(By.name("e_clave")).sendKeys(password);
		driver.findElement(By.name("b_acceso")).click();

		List<WebElement> menu = driver.findElements(By
				.className("menu-sub-item"));

		for (WebElement item : menu) {
			System.out.println(item.toString());
		}

		driver.switchTo().frame("mpagina");
		l.add(driver.getCurrentUrl());
		driver.switchTo().parentFrame();
		driver.findElement(By.id("imenu1")).click();
		driver.findElement(By.id("imenu1-4")).click();

		driver.switchTo().frame("mpagina");
		l.add(driver.getCurrentUrl());

		
		

		
		return l;

	}

}
