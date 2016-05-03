package ec.peleusi.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.InternationalFormatter;

public class Formatos {

	static DecimalFormatSymbols simboloDecimal = new DecimalFormatSymbols();

	public Formatos() {
		super();
		simboloDecimal.setDecimalSeparator('.');
	}

	public AbstractFormatterFactory getDecimalFormat() {
		return decimalFormat;
	}

	public AbstractFormatterFactory getIntegerFormat() {
		return numericFormat;
	}

	public void setDecimalFormat(AbstractFormatterFactory decimalFormat) {
		this.decimalFormat = decimalFormat;
	}

	private AbstractFormatterFactory decimalFormat = new AbstractFormatterFactory() {
		@Override
		public AbstractFormatter getFormatter(JFormattedTextField tf) {
			NumberFormat format = new DecimalFormat("###.###", simboloDecimal);
			format.setMinimumFractionDigits(2);
			format.setMaximumFractionDigits(2);
			format.setRoundingMode(RoundingMode.HALF_UP);
			InternationalFormatter formatter = new InternationalFormatter(format);
			formatter.setValueClass(Double.class);
			formatter.setAllowsInvalid(false);
			formatter.setMinimum(0.0);
			formatter.setMaximum(Double.MAX_VALUE);
			return formatter;
		}
	};
	private AbstractFormatterFactory numericFormat = new AbstractFormatterFactory() {
		@Override
		public AbstractFormatter getFormatter(JFormattedTextField tf) {
			NumberFormat format = new DecimalFormat("#");
			format.setMinimumFractionDigits(0);
			format.setMaximumFractionDigits(0);
			format.setRoundingMode(RoundingMode.HALF_UP);
			format.setGroupingUsed(false);
			InternationalFormatter formatter = new InternationalFormatter(format);
			formatter.setValueClass(Integer.class);
			//formatter.setAllowsInvalid(false);
			formatter.setMinimum(0);
			formatter.setMaximum(Integer.MAX_VALUE);
			return formatter;
		}

	};
}
