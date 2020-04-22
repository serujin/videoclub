package application;

import template.Controller;

public class VideoClubApplication {
	/*
	 * Buenas pedro
	 * 
	 * Las clases del paquete view, tan solo llevan a cabo funciones de la interfaz, como por ejemplo cambiar de escena y esas cosas
	 * Las clases del paquete model se encargan de trabajar con la base de datos
	 * En la clase controller es donde está la miga, ya que es la clase que conecta GUI con BBDD, donde se añaden todos los listener y 
	 * 		se preparan los elementos para hacer consultas y actualizar la interfaz
	 * 
	 * Me hubiera gustado tener mas tiempo para dejar todo mas modular y ordenadito (me da un poco de TOC algún método), pero de verdad que no he 
	 * 		tenido mas tiempo por exámenes y etc.
	 * 
	 * Espero que te guste :D
	 */
	static Controller c;
	public static void main(String[] args) {
		initController(); 
	}
	public static void initController() {
		c = new Controller();
	}
	
}


