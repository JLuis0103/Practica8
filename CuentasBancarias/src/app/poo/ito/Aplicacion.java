package app.poo.ito;

import java.awt.HeadlessException;
import java.time.LocalDate;
import clases.ito.poo.CuentaBancaria;
import clases.ito.poo.CuentasBancarias;
import excepciones.ito.poo.excepcionBorrarCuenta;
import excepciones.ito.poo.excepcionCuentaExistente;
import excepciones.ito.poo.excepcionDeposito;
import excepciones.ito.poo.excepcionNumCuenta;
import excepciones.ito.poo.excepcionRetiro;
import excepciones.ito.poo.excepcionSaldo;

import javax.swing.JOptionPane;

public class Aplicacion {

	static CuentasBancarias c;

	static void menu() {
		inicializa();
		boolean aux = true;
		int respuesta = 0;
		while (aux) {
			String opciones = "Bienvenido a su banco de preferencia, elija una opcion: \n1) Agregar una cuenta nueva. "
					+ "\n2) Ver las cuentas existentes. \n3) Realizar un deposito. \n4) Realizar un retiro. "
					+ "\n5) Dar de baja una cuenta. \n6) Hacer una consulta. \n7) Salir.";
			respuesta = Integer.parseInt(JOptionPane.showInputDialog(opciones));
			try {
				switch (respuesta) {
					case 1: agregarCuenta(); break;
					case 2: mostrarCuentas(); break;
					case 3: hacerDeposito(); break;
					case 4: hacerRetiro(); break;
					case 5: borrarCuenta(); break;
					case 6: consulta(); break;
					case 7: aux = false; break;
					default: JOptionPane.showMessageDialog(null, "Ingrese una de las opciones mostradas por favor.");
					}
				} catch (NumberFormatException | HeadlessException | excepcionNumCuenta | excepcionSaldo | excepcionCuentaExistente 
						| excepcionDeposito | excepcionRetiro | excepcionBorrarCuenta e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				} 
		}	
	}
	

	// ========================================
	static CuentaBancaria capturarCuenta() throws NumberFormatException, HeadlessException, excepcionNumCuenta, excepcionSaldo {
		CuentaBancaria c = new CuentaBancaria();
		c.setNumCuenta(Long.parseLong(JOptionPane.showInputDialog("Ingrese el numero de cuenta: ")));
		c.setSaldo(Float.parseFloat(JOptionPane.showInputDialog("Ingrese el saldo de la cuenta: ")));
		c.setNomCliente(JOptionPane.showInputDialog("Ingrese el nombre del cliente: "));
		c.setFechaApertura(LocalDate.parse(JOptionPane.showInputDialog("Ingrese al fecha de apertura en formato (aaaa-mm-dd): ")));
		
		return c;
	}

	// ========================================
	static void inicializa() {
		c = new CuentasBancarias();
	}

	// ========================================
	static void agregarCuenta() throws NumberFormatException, HeadlessException, excepcionNumCuenta, excepcionSaldo, excepcionCuentaExistente {
		CuentaBancaria newCuenta;
			newCuenta = capturarCuenta();
			if (c.addItem(newCuenta)) 
				JOptionPane.showMessageDialog(null, "Se ha agredado una nueva cuenta.");
			else if (c.isFull())
				c.crecerArreglo();
			else if (c.existeItem(newCuenta))
				JOptionPane.showMessageDialog(null, "ERROR: La cuenta ya existe.");
	}

	// ========================================
	static void mostrarCuentas() {
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			String cuentas = "";
			for (int i = 0; i < c.getSize(); i++)
				cuentas = cuentas + "\n" + (c.getItem(i));
			JOptionPane.showMessageDialog(null,"Lista de cuentas registradas en nuestro sistema: "+ cuentas);
		}
	}

	// ========================================
	static void hacerDeposito() throws excepcionDeposito {
		int pos = 0;
		float cantidad = 0;
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			boolean aux = true;
			while (aux) {
				String cuentas = "";
				for (int i = 0; i < c.getSize(); i++)
					cuentas = cuentas + "\n" + (i + 1) + ") " + (c.getItem(i)).getNomCliente() + " | " + (c.getItem(i)).getNumCuenta();
				pos = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cuenta a la que desea hacer un deposito: " + cuentas));
				if ((c.getSize()) >= pos && pos >= 0) {
					cantidad = Float.parseFloat(JOptionPane.showInputDialog("Ingrese la cantidad a depositar: "));
					c.getItem(pos - 1).deposito(cantidad);
					JOptionPane.showMessageDialog(null, "El deposito se ha realizado exitosamente.");
					aux = false;
				} else
					JOptionPane.showMessageDialog(null, "ERROR: Cuenta inexistente");
			}
		}
	}

	// ========================================
	static void hacerRetiro() throws excepcionRetiro {
		int pos = 0;
		float cantidad = 0;
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			boolean aux = true;
			while (aux) {
				String cuentas = "";
				for (int i = 0; i < c.getSize(); i++)
					cuentas = cuentas + "\n" + (i + 1) + ") " + (c.getItem(i)).getNomCliente() + " | " + (c.getItem(i)).getNumCuenta();
				pos = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cuenta a la que desea hacer el retiro: " + cuentas));
				if ((c.getSize()) >= pos && pos > 0) {
					cantidad = Float.parseFloat(JOptionPane.showInputDialog("Ingrese la cantidad a retirar: "));
					if (!(c.getItem(pos - 1).getSaldo() < cantidad)) {
						c.getItem(pos - 1).retiro(cantidad);
						JOptionPane.showMessageDialog(null, "El retiro de ha realizado exitosamente.");
						aux = false;
					} else
						JOptionPane.showMessageDialog(null, "ERROR: Cantidad invalida (la cantidad es superior al saldo de la cuenta)");
				} else
					JOptionPane.showMessageDialog(null, "ERORR: Cuenta inexistente");
			}
		}
	}

	// ========================================
	static void borrarCuenta() throws excepcionBorrarCuenta {
		int pos = 0;
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			boolean aux = true;
			while (aux) {
				String cuentas = "";
				for (int i = 0; i < c.getSize(); i++)
					cuentas = cuentas + "\n" + (i + 1) + ") " + (c.getItem(i)).getNomCliente() + " | " + (c.getItem(i)).getNumCuenta();
				pos = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cuenta que desea dar de baja: " + cuentas));
				if ((c.getSize()) >= pos && pos > 0) {
					c.clear(c.getItem(pos - 1));
					JOptionPane.showMessageDialog(null, "La cuenta ha sido eliminada exitosamente.");
					aux = false;
				} else
					JOptionPane.showMessageDialog(null, "ERROR: Cuenta inexistente.");
			}
		}
	}

	// ========================================
	static void consulta() throws NumberFormatException, HeadlessException {
		int respuesta = 0;
		boolean ciclo = true;
		while (ciclo) {
			String opciones = "CONSTULTAS: \n1) Monto total de todas las cuentas. \n2) Monto promedio de todas las cuentas."
					+ "\n3) Cuentas con saldo superior de los $10,000.00 MXN."
					+ "\n4) Cuenta/s con mayor saldo. \n5) Cuenta/s con menor saldo. \n6) Salir.";
			respuesta = Integer.parseInt(JOptionPane.showInputDialog(opciones));
			switch (respuesta) {
			case 1: montoTotal(); ciclo = false; break;
			case 2: montoPromedio(); ciclo = false;	break;
			case 3: mayor10mil(); ciclo = false; break;
			case 4: saldoMax(); ciclo = false; break;
			case 5: saldoMin(); ciclo = false; break;
			case 6: ciclo = false; break;
			default: JOptionPane.showMessageDialog(null, "Ingrese una de las opciones mostradas por favor.");
			}
		}
	}

	// ========================================
	static void montoTotal() {
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			float montoTotal = 0;
			for (int i = 0; i < c.getSize(); i++)
				montoTotal = montoTotal + c.getItem(i).getSaldo();
			JOptionPane.showMessageDialog(null, "El monto total es de: $" + montoTotal + "MXN.");
		}
	}

	// ========================================
	static void montoPromedio() {
		float montoProm = 0;
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			float montoTotal = 0;
			for (int i = 0; i < c.getSize(); i++)
				montoTotal = montoTotal + c.getItem(i).getSaldo();
			montoProm = montoTotal / c.getSize();
			JOptionPane.showMessageDialog(null, "El monto promedio es de: $" + montoProm + "MXN");
		}
	}

	// ========================================
	static void mayor10mil() {
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			int vacio = 0;
			CuentaBancaria copia[] = new CuentaBancaria[c.getSize()];
			for (int i = 0; i < c.getSize(); i++)
				if (c.getItem(i).getSaldo() >= 10000)
					copia[i - vacio] = c.getItem(i);
				else
					vacio++;
			String cuentas = "";
			for (int j = 0; j < (c.getSize() - vacio); j++)
				cuentas = cuentas + "\n" + copia[j].getNumCuenta();
			JOptionPane.showMessageDialog(null, "Las cuentas que tienen un saldo mayor a $10,000.00 MXN son:\n" + cuentas);
		}
	}

	// ========================================
	static void saldoMax() {
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			int vacio = 0;
			float max = c.getItem(0).getSaldo();
			for (int i = 0; i < c.getSize(); i++)
				if (c.getItem(i).getSaldo() > max)
					max = c.getItem(i).getSaldo();
			CuentaBancaria copia[] = new CuentaBancaria[c.getSize()];
			for (int i = 0; i < c.getSize(); i++)
				if (c.getItem(i).getSaldo() == max)
					copia[i - vacio] = c.getItem(i);
				else
					vacio++;
			String cuentas = "";
			for (int j = 0; j < (c.getSize() - vacio); j++)
				cuentas = cuentas + "\n" + copia[j].getNumCuenta();
			JOptionPane.showMessageDialog(null, "La/las cuenta/cuentas con mayor saldo es/son:\n" + cuentas);
		}

	}

	// ========================================
	static void saldoMin() {
		if (c.isFree())
			JOptionPane.showMessageDialog(null, "No se encontro ninguna cuenta registrada en el sistema.");
		else {
			int vacio = 0;
			float min = c.getItem(0).getSaldo();
			for (int i = 0; i < c.getSize(); i++)
				if (c.getItem(i).getSaldo() < min)
					min = c.getItem(i).getSaldo();
			CuentaBancaria copia[] = new CuentaBancaria[c.getSize()];
			for (int i = 0; i < c.getSize(); i++)
				if (c.getItem(i).getSaldo() == min)
					copia[i - vacio] = c.getItem(i);
				else
					vacio++;
			String cuentas = "";
			for (int j = 0; j < (c.getSize() - vacio); j++)
				cuentas = cuentas + "\n" + copia[j].getNumCuenta();
			JOptionPane.showMessageDialog(null, "La/las cuenta/cuentas con menor saldo es/son:\n" + cuentas);
		}
	}

}
