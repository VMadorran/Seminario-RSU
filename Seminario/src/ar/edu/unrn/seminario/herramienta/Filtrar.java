package ar.edu.unrn.seminario.herramienta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Filtrar {
	public static <T> List<T> filtrar(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}
	public static <T> List <T> filtrar(List <T> list , Comparator<T> comparator){
        List <T> resultado = new ArrayList<>();
        Collections.sort(list,comparator);
        return list;
    }
}
