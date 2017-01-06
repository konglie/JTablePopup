package com.kurungkurawal.PopComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Konglie on 1/6/2017.
 * konglie@kurungkurawal.com
 */
public class PopupCombo extends JTextField implements WindowFocusListener {
	private PopupCombo self;
	private JComponent popChild;
	private PopupComponent popupComponent;
	private PopupFactory popupFactory;
	private Popup pop;
	private JPanel popBody;
	private Window parent;
	private boolean isShowingPop = false;

	private JPopupMenu popupMenu;
	public PopupCombo(PopupComponent pop){
		// todo: remove the size from super constructor
		super(50);
		self = this;
		popupFactory = PopupFactory.getSharedInstance();
		popupComponent = pop;
		popupComponent.setPopupCombo(self);
		popChild = pop.getComponent();
		popBody = new JPanel(new BorderLayout());
		popBody.setOpaque(true);
		popBody.setFocusable(true);
		popBody.add(popChild, BorderLayout.CENTER);

		popBody.setBackground(Color.LIGHT_GRAY);
		popBody.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		initEvents();
	}

	private void initEvents(){
		self.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
					hidePopup();
					return;
				}
				shopPopup();
				String search = self.getText();
				self.popupComponent.filter(search);
			}
		});

		self.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				shopPopup();
			}
		});

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if(!isShowingPop){
					return;
				}
				if(event instanceof MouseEvent){
					MouseEvent m = (MouseEvent)event;
					if(m.getID() == MouseEvent.MOUSE_CLICKED)
					{
						if(!mouseOnComponent(self) && !mouseOnComponent(popBody)){
							hidePopup();
						}
					}
				}
			}
		}, AWTEvent.MOUSE_EVENT_MASK);
	}

	private boolean mouseOnComponent(JComponent component)
	{
		try {
			Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
			Rectangle componentRect = component.getBounds();
			componentRect.setLocation(component.getLocationOnScreen());
			return componentRect.contains(mouseLocation);
		} catch (Exception e){
			return false;
		}
	}

	private void makeAndShowPopup(){
		if(SwingUtilities.getWindowAncestor(self) != parent){
			if(parent != null){
				parent.removeWindowFocusListener(self);
			}

			parent = SwingUtilities.getWindowAncestor(self);
			parent.addWindowFocusListener(self);
		}

		Point location = getLocationOnScreen();
		Dimension prefWidth = self.popBody.getPreferredSize();
		prefWidth.width = getWidth();
		prefWidth.height = Math.min(prefWidth.height, parent.getHeight() / 2);
		popBody.setPreferredSize(prefWidth);
		pop = popupFactory.getPopup(
				self,
				popBody,
				location.x,
				location.y + getHeight()
		);
		pop.show();
	}

	public void shopPopup(){
		if(isShowingPop){
			return;
		}

		if(self.pop != null){
			self.pop.show();
		} else {
			makeAndShowPopup();
		}
		self.isShowingPop = true;
		popupComponent.showPop();
	}

	public void hidePopup(){
		if(pop != null){
			pop.hide();
			pop = null;
			popupComponent.hidePop();
		}
		isShowingPop = false;
	}

	public Object getSelected(){
		return popupComponent.getSelected();
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {

	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		hidePopup();
	}
}
