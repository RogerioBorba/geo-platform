/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2011 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details. You should have received a copy of the GNU General
 * Public License along with this program. If not, see http://www.gnu.org/licenses/
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library. Thus, the terms and
 * conditions of the GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you permission
 * to link this library with independent modules to produce an executable, regardless
 * of the license terms of these independent modules, and to copy and distribute
 * the resulting executable under terms of your choice, provided that you also meet,
 * for each linked independent module, the terms and conditions of the license of
 * that module. An independent module is a module which is not derived from or
 * based on this library. If you modify this library, you may extend this exception
 * to your version of the library, but you are not obligated to do so. If you do not
 * wish to do so, delete this exception statement from your version.
 *
 */
package org.geosdi.geoplatform.gui.server.gwt;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import java.util.List;
import org.geosdi.geoplatform.gui.client.service.UserRemote;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.geosdi.geoplatform.gui.global.GeoPlatformException;
import org.geosdi.geoplatform.gui.global.security.IGPUserManageDetail;
import org.geosdi.geoplatform.gui.server.IUserService;
import org.geosdi.geoplatform.gui.server.service.impl.UserService;
import org.geosdi.geoplatform.gui.spring.GeoPlatformContextUtil;

/**
 *
 * @author Vincenzo Monteverde
 * @email vincenzo.monteverde@geosdi.org - OpenPGP key ID 0xB25F4B38
 */
public class UserRemoteImpl extends RemoteServiceServlet implements UserRemote {

    private static final long serialVersionUID = 6832264543827688477L;
    //
    private IUserService userService = (IUserService) GeoPlatformContextUtil.getInstance().
            getBean(UserService.class);

    @Override
    public long insertUser(IGPUserManageDetail userDetail) throws GeoPlatformException {
        return userService.insertUser(userDetail, super.getThreadLocalRequest());
    }

    @Override
    public long updateUser(IGPUserManageDetail userDetail) throws GeoPlatformException {
        return userService.updateUser(userDetail, super.getThreadLocalRequest());
    }

    @Override
    public boolean deleteUser(long userId) throws GeoPlatformException {
        return userService.deleteUser(userId, super.getThreadLocalRequest());
    }

    @Override
    public List<IGPUserManageDetail> getUsers() {
        return userService.getUsers(super.getThreadLocalRequest());
    }

    @Override
    public List<IGPUserManageDetail> searchUsers(PagingLoadConfig config) {
        return userService.searchUsers(config, super.getThreadLocalRequest());
    }
}