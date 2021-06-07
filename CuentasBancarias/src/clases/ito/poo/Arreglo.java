package clases.ito.poo;

import excepciones.ito.poo.excepcionBorrarCuenta;
import excepciones.ito.poo.excepcionCuentaExistente;

public interface Arreglo<T> {

	public boolean addItem(T item) throws excepcionCuentaExistente;

	public boolean existeItem(T item);
	
	public T getItem(int pos);

	public int getSize();

	public boolean clear(T item) throws excepcionBorrarCuenta;

	public boolean isFree();

	public boolean isFull();
	
}
