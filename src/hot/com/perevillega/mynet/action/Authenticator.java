/**
* My Net is free software, you can redistribute it and/or modify
* it under the terms of GNU Affero General Public License
* as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
*
* You should have received a copy of the the GNU Affero
* General Public License, along with My Net. If not, see
*
* http://www.gnu.org/licenses/agpl.txt
*/

package com.perevillega.mynet.action;

import javax.ejb.Local;

@Local
public interface Authenticator {

    boolean authenticate();

}
