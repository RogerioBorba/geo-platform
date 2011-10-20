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
package org.geosdi.geoplatform.gui.client.widget.pagination;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import org.geosdi.geoplatform.gui.client.model.GPUserManageDetail;
import org.geosdi.geoplatform.gui.client.model.GPUserManageDetailKeyValue;
import org.geosdi.geoplatform.gui.client.service.UserRemote;
import org.geosdi.geoplatform.gui.client.service.UserRemoteAsync;
import org.geosdi.geoplatform.gui.client.widget.SearchStatus.EnumSearchStatus;
import org.geosdi.geoplatform.gui.client.widget.grid.pagination.GeoPlatformSearchWidget;
import org.geosdi.geoplatform.gui.global.GeoPlatformException;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email  giuseppe.lascaleia@geosdi.org
 */
public class ManageUsersPagWidget
        extends GeoPlatformSearchWidget<GPUserManageDetail> {

    private UserRemoteAsync userRemote = UserRemote.Util.getInstance();
    private int pageSize = 10;

    public ManageUsersPagWidget() {
        super(true);
    }

    @Override
    public void setWindowProperties() {
        super.setHeading("GeoPlatform Users Management");
        super.setSize(600, 490);

        super.addWindowListener(new WindowListener() {

            @Override
            public void windowShow(WindowEvent we) {
                searchText = "";
                loader.load(0, pageSize);
            }
        });
    }

    @Override
    public void createStore() {
        super.toolBar = new PagingToolBar(pageSize);

        super.proxy = new RpcProxy<PagingLoadResult<GPUserManageDetail>>() {

            @Override
            protected void load(Object loadConfig,
                    AsyncCallback<PagingLoadResult<GPUserManageDetail>> callback) {

                userRemote.searchUsers((PagingLoadConfig) loadConfig,
                        searchText, callback);
            }
        };

        super.loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
        super.loader.setRemoteSort(false);

        super.store = new ListStore<GPUserManageDetail>(loader);

        super.toolBar.bind(loader);

//        this.setUpLoadListener();
    }

    @Override
    public void setGridProperties() {
        super.grid.setWidth(530);
        super.grid.setHeight(250);
    }

    @Override
    public ColumnModel prepareColumnModel() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig nameColumn = new ColumnConfig();
        nameColumn.setId(GPUserManageDetailKeyValue.NAME.toString());
        nameColumn.setHeader("Name");
        nameColumn.setWidth(340);
        configs.add(nameColumn);

        ColumnConfig userNameColumn = new ColumnConfig();
        userNameColumn.setId(GPUserManageDetailKeyValue.USERNAME.toString());
        userNameColumn.setHeader("User Name");
        userNameColumn.setWidth(130);
        configs.add(userNameColumn);

        ColumnConfig roleColumn = new ColumnConfig();
        roleColumn.setId(GPUserManageDetailKeyValue.AUTORITHY.toString());
        roleColumn.setHeader("Role");
        roleColumn.setWidth(50);
        configs.add(roleColumn);

        return new ColumnModel(configs);
    }

    @Override
    public void select() {
        System.out.println("@@@ TODO select()");
    }

    @Override
    public void show() {
        super.init();
        super.show();
    }

    private void setUpLoadListener() {
        super.loader.addLoadListener(new LoadListener() {

            @Override
            public void loaderBeforeLoad(LoadEvent le) {
                searchStatus.setBusy("Connection to the Server");
                if (select.isEnabled()) {
                    select.disable();
                }
            }

            @Override
            public void loaderLoad(LoadEvent le) {
                setSearchStatus(EnumSearchStatus.STATUS_SEARCH,
                        EnumSearchStatus.STATUS_MESSAGE_SEARCH);
            }

            @Override
            public void loaderLoadException(LoadEvent le) {
                clearGridElements();
                try {
                    throw le.exception;
                } catch (GeoPlatformException e) {
                    setSearchStatus(EnumSearchStatus.STATUS_NO_SEARCH,
                            EnumSearchStatus.STATUS_MESSAGE_NOT_SEARCH);
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    setSearchStatus(EnumSearchStatus.STATUS_SEARCH_ERROR,
                            EnumSearchStatus.STATUS_MESSAGE_SEARCH_ERROR);
                }
            }
        });
    }
}
