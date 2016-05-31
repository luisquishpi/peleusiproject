package ec.peleusi.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.text.View;

public class LinkViewRadioButtonUI extends BasicRadioButtonUI {
	// private static final LinkViewRadioButtonUI radioButtonUI = new
		// LinkViewRadioButtonUI();
		// private boolean defaults_initialized = false;
		private static Dimension size = new Dimension();
		private static Rectangle viewRect = new Rectangle();
		private static Rectangle iconRect = new Rectangle();
		private static Rectangle textRect = new Rectangle();

		// public static ComponentUI createUI(JComponent b) {
		// return radioButtonUI;
		// }
		// @Override protected void installDefaults(AbstractButton b) {
		// super.installDefaults(b);
		// if (!defaults_initialized) {
		// icon = null; //UIManager.getIcon(getPropertyPrefix() + "icon");
		// defaults_initialized = true;
		// }
		// }
		// @Override protected void uninstallDefaults(AbstractButton b) {
		// super.uninstallDefaults(b);
		// defaults_initialized = false;
		// }
		@Override
		public Icon getDefaultIcon() {
			return null;
		}

		@Override
		public synchronized void paint(Graphics g, JComponent c) {
			// AbstractButton b = (AbstractButton) c;
			Font f = c.getFont();
			g.setFont(f);
			FontMetrics fm = c.getFontMetrics(f);

			Insets i = c.getInsets();
			c.getSize(size);
			viewRect.x = i.left;
			viewRect.y = i.top;
			viewRect.width = size.width - i.right - viewRect.x;
			viewRect.height = size.height - i.bottom - viewRect.y;
			iconRect.setBounds(0, 0, 0, 0); // .x = iconRect.y = iconRect.width =
											// iconRect.height = 0;
			textRect.setBounds(0, 0, 0, 0); // .x = textRect.y = textRect.width =
											// textRect.height = 0;

			if (c.isOpaque()) {
				g.setColor(c.getBackground());
				g.fillRect(0, 0, size.width, size.height);
			}

			String text;
			AbstractButton b;
			if (c instanceof AbstractButton) {
				b = (AbstractButton) c;
				text = SwingUtilities.layoutCompoundLabel(b, fm, b.getText(), null, // altIcon
																					// !=
																					// null
																					// ?
																					// altIcon
																					// :
																					// getDefaultIcon(),
						b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(),
						b.getHorizontalTextPosition(), viewRect, iconRect, textRect, 0); // b.getText()
																							// ==
																							// null
																							// ?
																							// 0
																							// :
																							// b.getIconTextGap());
			} else {
				return;
			}

			// // Changing Component State During Painting (an infinite repaint
			// loop)
			// // pointed out by Peter
			// // -note:
			// http://today.java.net/pub/a/today/2007/08/30/debugging-swing.html#changing-component-state-during-the-painting
			// //b.setForeground(Color.BLUE);
			// if (!model.isEnabled()) {
			// //b.setForeground(Color.GRAY);
			// } else if (model.isPressed() && model.isArmed() ||
			// model.isSelected()) {
			// //b.setForeground(Color.BLACK);
			// } else if (b.isRolloverEnabled() && model.isRollover()) {

			ButtonModel model = b.getModel();
			g.setColor(c.getForeground());
			if (!model.isSelected() && !model.isPressed() && !model.isArmed() && b.isRolloverEnabled()
					&& model.isRollover()) {
				g.drawLine(viewRect.x, viewRect.y + viewRect.height, viewRect.x + viewRect.width,
						viewRect.y + viewRect.height);
			}
			View v = (View) c.getClientProperty(BasicHTML.propertyKey);
			if (Objects.nonNull(v)) {
				v.paint(g, textRect);
			} else {
				paintText(g, c, textRect, text);
			}
		}
}
