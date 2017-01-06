package com.kurungkurawal.PopComponent;

import javax.swing.*;

/**
 * Created by Konglie on 1/6/2017.
 * konglie@kurungkurawal.com
 */
public interface PopupComponent {
	JComponent getComponent();
	void filter(String search);
	void showPop();
	void hidePop();
	void setPopupCombo(PopupCombo popupCombo);
	Object getSelected();
}
