package ec.peleusi.notification;

public enum Notifications implements Notification {

	INFORMATION("/ec/peleusi/notification/images/info.png", "#2C54AB"),
	NOTICE("/ec/peleusi/notification/images/notice.png", "#8D9695"),
	SUCCESS("/ec/peleusi/notification/images/success.png", "#009961"),
	WARNING("/ec/peleusi/notification/images/warning.png", "#E23E0A"),
	ERROR("/ec/peleusi/notification/images/error.png", "#CC0033");

	private final String urlResource;
	private final String paintHex;

	Notifications(String urlResource, String paintHex) {
		this.urlResource = urlResource;
		this.paintHex = paintHex;
	}

	@Override
	public String getURLResource() {
		return urlResource;
	}

	@Override
	public String getPaintHex() {
		return paintHex;
	}
}
