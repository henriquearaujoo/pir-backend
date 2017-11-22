package com.samsung.fas.pir.utils;

public class CNPValidator {
	private static final int[] CPF 		= {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	private static final int[] CNPJ 	= {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	private static int digitcalc(String str, int[] peso) {
		int soma = 0;
		for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt(str.substring(indice,indice+1));
			soma += digito*peso[peso.length-str.length()+indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValidCPF(String cpf) {
		if ((cpf==null) || (cpf.length()!=11) || (isSameCharacters(cpf)))
			return false;

		Integer d1 = digitcalc(cpf.substring(0,9), CPF);
		Integer d2 = digitcalc(cpf.substring(0,9) + d1, CPF);
		return cpf.equals(cpf.substring(0,9) + d1.toString() + d2.toString());
	}

	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj==null)||(cnpj.length()!=14) || (isSameCharacters(cnpj)))
			return false;

		Integer d1 = digitcalc(cnpj.substring(0,12), CNPJ);
		Integer d2 = digitcalc(cnpj.substring(0,12) + d1, CNPJ);
		return cnpj.equals(cnpj.substring(0,12) + d1.toString() + d2.toString());
	}

	private static boolean isSameCharacters(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(0) != string.charAt(i))
				return false;
		}
		return true;
	}
}
