package cn.saury.core.Helper.Auth;

public interface ISession {
	String getId();
	Object getAttr(String name);
	ISession setAttr(String name, Object value);
	String[] getAttrNames();
	void removeAttr(String name);
	void invalidate(String accessToken);
}
