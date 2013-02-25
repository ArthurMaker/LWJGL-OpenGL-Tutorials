package com.ra4king.opengl.util.interpolators;

import com.ra4king.opengl.util.math.Vector;

import java.util.ArrayList;

public class ConstVelLinearInterpolatorVector<T extends Vector<T>> extends WeightedLinearInterpolatorVector<T> {
	private float totalDist;

	public float getDistance() {
		return totalDist;
	}

	public void setValues(ArrayList<T> data) {
		setValues(data, true);
	}

	public void setValues(ArrayList<T> data, boolean isLooping) {
		values.clear();

		for(T d : data)
			values.add(new Data(d, 0));

		if(isLooping)
			values.add(values.get(0));

		totalDist = 0;
		for(int a = 1; a < values.size(); a++) {
			totalDist += values.get(a).data.copy().sub(values.get(a - 1).data).length();
			values.get(a).weight = totalDist;
		}

		for(Data d : values)
			d.weight /= totalDist;
	}
}
