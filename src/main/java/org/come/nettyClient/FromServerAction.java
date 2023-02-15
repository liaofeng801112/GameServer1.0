package org.come.nettyClient;

import java.io.IOException;

public interface FromServerAction {

	public void controlMessFromServer(String mes) throws IOException;

}
