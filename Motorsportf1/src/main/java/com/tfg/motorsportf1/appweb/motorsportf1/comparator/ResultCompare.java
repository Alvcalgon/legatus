package com.tfg.motorsportf1.appweb.motorsportf1.comparator;

import java.util.Comparator;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Result;

public class ResultCompare implements Comparator<Result> {

	@Override
	public int compare(Result r1, Result r2) {
		return r1.compareTo(r2);
	}

}
