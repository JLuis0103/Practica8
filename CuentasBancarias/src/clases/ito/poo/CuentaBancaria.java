package clases.ito.poo;

import java.time.LocalDate;

import excepciones.ito.poo.excepcionDeposito;
import excepciones.ito.poo.excepcionNumCuenta;
import excepciones.ito.poo.excepcionRetiro;
import excepciones.ito.poo.excepcionSaldo;

public class CuentaBancaria implements Comparable<CuentaBancaria> {
	
	private long numCuenta = 0L;
	private String nomCliente = "";
	private float saldo = 0F;
	private LocalDate fechaApertura = null;
	private LocalDate fechaActualizacion = null;
	
	private void verifNumCuenta(long numCuenta) throws excepcionNumCuenta{
		if (numCuenta <= 9999)
			throw new excepcionNumCuenta("ERROR: No es posible utilizar un numero de cuenta inferior a 5 digitos.");
		else
			this.numCuenta = numCuenta;
	}
	
	private void verifSaldo(float saldo) throws excepcionSaldo{
		if (saldo < 5000F)
			throw new excepcionSaldo("ERROR: Se necesito minimo $5,000.00 MXN para abrir una cuenta.");
		else
			this.saldo = saldo;
	}
	
	private boolean verifDeposito(float cantidad) throws excepcionDeposito{
		boolean deposito = true;
		if (cantidad < 500 || cantidad >25000) {
			deposito = false;
			throw new excepcionDeposito ("ERROR: Unicamente depositos con un valor entre $500.00 y $25,000.00");
		}
		return deposito;
	}
	
	private boolean verifRetiro(float cantidad) throws excepcionRetiro{
		boolean retiro = true;
		if (!((cantidad%100) == 0) || cantidad < 100 || cantidad > 6000) {
			retiro = false;
			throw new excepcionRetiro ("ERROR: Unicamente retiros con un valor entre $100.00 y $6,000.00, ademas que debe ser dividible entre 100");
		}
		return retiro;
	}
	
	
	//====================================
	public CuentaBancaria() {
		super();
	}
	
	public CuentaBancaria(long numCuenta, String nomCliente, float saldo, LocalDate fechaApertura) throws excepcionNumCuenta, excepcionSaldo {
		super();
		verifNumCuenta(numCuenta);
		this.nomCliente = nomCliente;
		verifSaldo(saldo);
		this.fechaApertura = fechaApertura;
	}
	
	//====================================
	public boolean deposito(float cantidad) throws excepcionDeposito {
		boolean deposito = false;
		if (verifDeposito(cantidad) == true ) {
			LocalDate c=LocalDate.now();
			if (this.fechaApertura.compareTo(c) < 0) {
				deposito=true;
				this.saldo=this.saldo+cantidad;
				this.fechaActualizacion=LocalDate.now();
			}
		}
		return deposito;
	}

	//====================================
	public boolean retiro(float cantidad) throws excepcionRetiro {
		boolean retiro = false;
		if (verifRetiro(cantidad) == true) {
			if (cantidad <= this.saldo) {
				retiro=true;
				this.saldo=this.saldo-cantidad;
				this.fechaActualizacion=LocalDate.now();
			}
		}
		return retiro;
	}

	//====================================
	public long getNumCuenta() {
		return this.numCuenta;
	}

	//====================================
	public void setNumCuenta(long newNumCuenta) throws excepcionNumCuenta {
		verifNumCuenta(newNumCuenta);;
	}

	//====================================
	public String getNomCliente() {
		return this.nomCliente;
	}

	//====================================
	public void setNomCliente(String newNomCliente) {
		this.nomCliente = newNomCliente;
	}

	//====================================
	public float getSaldo() {
		return this.saldo;
	}

	//====================================
	public void setSaldo(float newSaldo) throws excepcionSaldo {
		verifSaldo(newSaldo);
	}

	//====================================
	public LocalDate getFechaApertura() {
		return this.fechaApertura;
	}

	//====================================
	public void setFechaApertura(LocalDate newFechaApertura) {
		this.fechaApertura = newFechaApertura;
	}

	//====================================
	public LocalDate getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	//====================================
	public void setFechaActualizacion(LocalDate newFechaActualizacion) {
		this.fechaActualizacion = newFechaActualizacion;
	}

	@Override
	public String toString() {
		return "Numero de cuenta: " + numCuenta + " | Cliente: " + nomCliente + "\nSaldo: " + saldo
				+ "\nFecha Apertura: " + fechaApertura + " | Fecha Actualizacion: " + fechaActualizacion + "\n";
	}

	@Override
	public int compareTo(CuentaBancaria o) {
		int compare=0;
		if (this.numCuenta<o.getNumCuenta())
				compare=-1;
		else if(this.numCuenta>o.getNumCuenta())
				compare=1;
		return compare;
	}

	

}
