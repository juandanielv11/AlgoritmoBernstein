/**
 * 
 */
package com.anstek.negocio;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;

/**
 * @author ddmurillo
 *
 */
public class Bernstein {
	private Atributo[] atributos;
	
	private DependenciaFuncional[] dependencias;
	
	/**
	 * Constructor
	 */
	public Bernstein(){}
	
	/**
	 * Constructor que recibe un arreglo de atributos y uno de dependencias funcionales
	 * @param atributos
	 * @param dependencias
	 */
	public Bernstein(Atributo[] atributos, DependenciaFuncional[] dependencias){
		this.setAtributos(atributos);
		this.setDependencias(dependencias);
	}

	public DependenciaFuncional[] getDependencias() {
		return dependencias;
	}

	public void setDependencias(DependenciaFuncional[] dependencias) {
		this.dependencias = dependencias;
	}

	public Atributo[] getAtributos() {
		return atributos;
	}

	public void setAtributos(Atributo[] atributos) {
		this.atributos = atributos;
	}
	
	/**
	 * Normaliza las dependencias funcionales usando el algortimo de Bernstein
	 * @return
	 */
	public TreeMap<String,HashSet<String>> NormalizadorBernstein(){
		// Lado derecho Sipmle
		DependenciaFuncional[] df1 = LDS.LadoDerechoSimple(this.dependencias);
		
		// Limpia atributos extra�os
		DependenciaFuncional[] df2 = AtributosExtranios.LimpiaAtributosExtranios(df1);
		
		// Quita DF redundantes
		/// TODO
		
		// Obtiene relaciones 
		TreeMap<String,HashSet<String>> rel = Particion.ParticionarRelaciones(df2);
		
		// Obtiene el nombre de los atributos de acuerdo a los Ids de las relaciones
		TreeMap<String,HashSet<String>> result = new TreeMap<String, HashSet<String>>();		
		for (Map.Entry<String,HashSet<String>> r : rel.entrySet()) {
			String k = r.getKey().replace("[","").replace("]", "");
			String[] keys = k.split(",");
			
			HashSet<String> knames = new HashSet<String>();
			
			for (int i = 0; i < keys.length; i++) {
				knames.add(Atributo.retornarNombreDatoCodigo(keys[i], this.getAtributos()));
			}
			System.out.println(knames);
			HashSet<String> vnames = new HashSet<String>();
			for (String s : r.getValue()) {
				vnames.add(Atributo.retornarNombreDatoCodigo(s, this.getAtributos()));
			}
			System.out.println(vnames);
			result.put(knames.toString(), vnames);
		}
		
		return result;
	}

}
