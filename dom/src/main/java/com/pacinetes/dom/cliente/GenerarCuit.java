/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.pacinetes.dom.cliente;

public class GenerarCuit {
	private static int dniStc;

	private static int xyStc;

	private static int digitoStc;

	public static String generar(Sexo s, int nDoc) {

		if (s.name() == "Masculino")
			xyStc = 20;
		else
			xyStc = 27;
		dniStc = nDoc;
		calcular();
		return formatear();
	}

	private static String formatear() {
		return String.valueOf(xyStc) + "-" + completar(String.valueOf(dniStc)) + "-" + String.valueOf(digitoStc);
	}

	private static String completar(String dniStr) {
		int n = dniStr.length();
		while (n < 8) {
			dniStr = "0" + dniStr;
			n = dniStr.length();
		}
		return dniStr;
	}

	private static void calcular() {
		long tmp1, tmp2;
		long acum = 0;
		int n = 2;
		tmp1 = xyStc * 100000000L + dniStc;
		for (int i = 0; i < 10; i++) {
			tmp2 = tmp1 / 10;
			acum += (tmp1 - tmp2 * 10L) * n;
			tmp1 = tmp2;
			if (n < 7)
				n++;
			else
				n = 2;
		}
		n = (int) (11 - acum % 11);
		if (n == 10) {
			xyStc = 23;
			calcular();
		} else {
			if (n == 11)
				digitoStc = 0;
			else
				digitoStc = n;
		}
	}
}