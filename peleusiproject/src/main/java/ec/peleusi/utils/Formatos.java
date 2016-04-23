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

    public Formatos() {
        super();
    }

    public AbstractFormatterFactory getDecimalFormat() {
        return decimalFormat;
    }
    public AbstractFormatterFactory getNumericFormat() {
        return numericFormat;
    }
    public void setDecimalFormat(AbstractFormatterFactory decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    private AbstractFormatterFactory decimalFormat = new AbstractFormatterFactory() {
        @Override
        public AbstractFormatter getFormatter(JFormattedTextField tf) {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            // simbolos.setGroupingSeparator(',');
            NumberFormat format = new DecimalFormat("###.###", simbolos);
            // NumberFormat format = DecimalFormat.getInstance();
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.HALF_UP);
            InternationalFormatter formatter = new InternationalFormatter(format);
            formatter.setAllowsInvalid(false);
            formatter.setMinimum(0.0);
            // formatter.setMaximum(9999.00);
            return formatter;
        }
    };
    private AbstractFormatterFactory numericFormat = new AbstractFormatterFactory() {
        @Override
        public AbstractFormatter getFormatter(JFormattedTextField tf) {
           
        	
        	DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            // simbolos.setGroupingSeparator(',');
            NumberFormat format = new DecimalFormat("#", simbolos);
            // NumberFormat format = DecimalFormat.getInstance();
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(0);
            format.setRoundingMode(RoundingMode.HALF_UP);
            InternationalFormatter formatter = new InternationalFormatter(format);
            formatter.setAllowsInvalid(false);
            formatter.setMinimum(0);
            // formatter.setMaximum(9999.00);
            return formatter;
        }
        
    };
}
