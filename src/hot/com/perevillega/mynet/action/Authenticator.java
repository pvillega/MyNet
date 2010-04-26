package com.perevillega.mynet.action;

import javax.ejb.Local;

@Local
public interface Authenticator {

    boolean authenticate();

}
