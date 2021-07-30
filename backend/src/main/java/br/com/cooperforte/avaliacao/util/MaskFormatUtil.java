package br.com.cooperforte.avaliacao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public class MaskFormatUtil {

	public static final String TELEFONE_REGEX_PATTERN = "^$|(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";

	public String notEmpty(String campo) {
		if (StringUtils.isEmpty(campo)) {
			return "-";
		}
		return campo;
	}

	public String formatCpf(String nuCpf) {
		try {
			if (nuCpf == null || nuCpf.trim().length() == 0) {
				return "";
			}
			MaskFormatter cpfFormat = new MaskFormatter("###.###.###-##");
			cpfFormat.setValueContainsLiteralCharacters(false);
			return cpfFormat.valueToString(nuCpf.trim());
		} catch (ParseException e) {
			System.out.println("[ERROR] Exception while formatString " + e);
		}
		return null;
	}

	public String formatCnpj(String nuCnpj) {
		try {
			if (nuCnpj == null || nuCnpj.trim().length() == 0) {
				return "";
			}
			MaskFormatter format = new MaskFormatter("##.###.###/####-##");
			format.setValueContainsLiteralCharacters(false);
			return format.valueToString(nuCnpj.trim());
		} catch (ParseException e) {
			System.out.println("[ERROR] Exception while formatString " + e);
		}
		return null;
	}

	public static String formatDate(Date data) {
		if (data == null) {
			return "";
		}
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(data);
	}

	public String formatDateHour(Date data) {
		if (data == null) {
			return "";
		}
		String pattern = "dd/MM/yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(data);
	}

	public String formatLatitude(BigDecimal latitude) {

		if (latitude == null) {
			return "-";
		}

		String cardinal = latitude.compareTo(BigDecimal.ZERO) >= 0 ? "norte" : "sul";
		return formatLatLong(latitude) + " - Orientação " + cardinal;
	}

	public String formatLongitude(BigDecimal longitude) {

		if (longitude == null) {
			return "-";
		}

		String cardinal = longitude.compareTo(BigDecimal.ZERO) >= 0 ? "leste" : "oeste";
		return formatLatLong(longitude) + " - Orientação " + cardinal;
	}

	private String formatLatLong(BigDecimal latLong) {

		Double absolute = Math.abs(latLong.doubleValue());
		Double degrees = Math.floor(absolute);
		Double minutesNotTruncated = (absolute - degrees) * 60;
		Double minutes = Math.floor(minutesNotTruncated);
		Double seconds = Math.floor((minutesNotTruncated - minutes) * 60);

		return degrees.intValue() + "(g) " + minutes.intValue() + "(m) " + seconds.intValue() + "(s)";
	}

	public String formatHtml(String campo) {
		if (StringUtils.isEmpty(campo)) {
			return "-";
		}

		return campo.replaceAll("<[^>]*>", "");
	}

	public static String getCpfWithMask(String nuCpf) {
		MaskFormatter cpfFormat = null;
		try {
			cpfFormat = new MaskFormatter("###.###.###-##");
			cpfFormat.setValueContainsLiteralCharacters(false);
			return cpfFormat.valueToString(nuCpf);
		} catch (ParseException e) {
			Logger.getGlobal().log(Level.ALL, "[ERROR] Exception while formatString " + e.getMessage());
			Logger.getGlobal().log(Level.SEVERE, "[ERROR] Exception while formatString " + e.getMessage(), e);
		}
		return null;
	}

	public static String getCnpjWithMask(String nuCnpj) {
		MaskFormatter format = null;
		try {
			format = new MaskFormatter("##.###.###/####-##");
			format.setValueContainsLiteralCharacters(false);
			return format.valueToString(nuCnpj);
		} catch (ParseException e) {
			Logger.getGlobal().log(Level.ALL, "[ERROR] Exception while formatString " + e.getMessage());
			Logger.getGlobal().log(Level.SEVERE, "[ERROR] Exception while formatString " + e.getMessage(), e);
		}
		return null;
	}

	public static String getTelWithMask(String nuTelefone) {
		MaskFormatter format = null;
		try {
			if (nuTelefone == null) {
				return null;
			}
			if (nuTelefone.length() == 11) {
				format = new MaskFormatter("(##) # ####-####");
			} else {
				format = new MaskFormatter("(##) ####-####");
			}
			format.setValueContainsLiteralCharacters(false);
			return format.valueToString(nuTelefone);
		} catch (ParseException e) {
			Logger.getGlobal().log(Level.ALL, "[ERROR] Exception while formatString " + e.getMessage());
			Logger.getGlobal().log(Level.SEVERE, "[ERROR] Exception while formatString " + e.getMessage(), e);
		}
		return null;
	}

	public static String getCepWithMask(String nuCep) {
		MaskFormatter format = null;
		try {
			format = new MaskFormatter("#####-###");
			format.setValueContainsLiteralCharacters(false);
			return format.valueToString(nuCep);
		} catch (ParseException e) {
			Logger.getGlobal().log(Level.ALL, "[ERROR] Exception while formatString " + e.getMessage());
			Logger.getGlobal().log(Level.SEVERE, "[ERROR] Exception while formatString " + e.getMessage(), e);
		}
		return null;
	}

	public static String getValorEmReais(String vl) {
		if (vl == null) {
			return "0,00";
		}
		NumberFormat brFormatFloat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		return brFormatFloat.format(Float.valueOf(vl));
	}

}
