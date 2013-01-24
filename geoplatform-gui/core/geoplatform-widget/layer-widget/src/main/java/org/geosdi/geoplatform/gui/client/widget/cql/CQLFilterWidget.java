/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2013 geoSDI Group (CNR IMAA - Potenza - ITALY).
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
package org.geosdi.geoplatform.gui.client.widget.cql;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.geosdi.geoplatform.gui.client.config.LayerModuleInjector;
import org.geosdi.geoplatform.gui.client.model.memento.save.IMementoSave;
import org.geosdi.geoplatform.gui.client.model.memento.save.storage.AbstractMementoOriginalProperties;
import org.geosdi.geoplatform.gui.client.service.LayerRemote;
import org.geosdi.geoplatform.gui.client.widget.GeoPlatformWindow;
import org.geosdi.geoplatform.gui.client.widget.tree.GPTreePanel;
import org.geosdi.geoplatform.gui.configuration.message.GeoPlatformMessage;
import org.geosdi.geoplatform.gui.impl.map.event.CQLFilterLayerMapEvent;
import org.geosdi.geoplatform.gui.model.tree.GPBeanTreeModel;
import org.geosdi.geoplatform.gui.model.tree.GPLayerTreeModel;
import org.geosdi.geoplatform.gui.puregwt.GPHandlerManager;

/**
 * @author Nazzareno Sileno - CNR IMAA geoSDI Group
 * @email nazzareno.sileno@geosdi.org
 */
public class CQLFilterWidget extends GeoPlatformWindow {

    public final static short WIDGET_HEIGHT = 400;
    public final static short WIDGET_WIDTH = 500;
    private final static String CQL_FILTER_HEADING = "CQL FILTER EDITOR";
    private final CQLFilterLayerMapEvent cqlFilterLayerMapEvent = new CQLFilterLayerMapEvent();
    private GPTreePanel<GPBeanTreeModel> treePanel;
    private CQLFilterTabWidget cqlFilterTabWidget;

    public CQLFilterWidget(boolean lazy, GPTreePanel<GPBeanTreeModel> treePanel) {
        super(lazy);
        this.treePanel = treePanel;
    }

    @Override
    public void addComponent() {
        this.cqlFilterTabWidget = new CQLFilterTabWidget(Boolean.TRUE, this.treePanel);
        super.add(this.cqlFilterTabWidget);
        Button verifyButton = new Button("Verify", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                LayerRemote.Util.getInstance().checkCQLExpression(cqlFilterTabWidget.getCQLFilterExpression(),
                        new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GeoPlatformMessage.errorMessage("CQL Error",
                                caught.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        CQLFilterWidget.super.setStateId(result);
                        if (result.startsWith("Error")) {
                            GeoPlatformMessage.errorMessage("CQL Error",
                                    result);
                        } else {
                            GeoPlatformMessage.alertMessage("CQL Success",
                                    result);
                        }
                    }
                });
            }
        });
        super.addButton(verifyButton);
        Button applyButton = new Button("Apply", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                GPLayerTreeModel layerSelected = (GPLayerTreeModel) treePanel.getSelectionModel().getSelectedItem();
                IMementoSave mementoSave = LayerModuleInjector.MainInjector.getInstance().getMementoSave();
                AbstractMementoOriginalProperties memento = mementoSave.copyOriginalProperties(layerSelected);
                layerSelected.setCqlFilter(cqlFilterTabWidget.getCQLFilterExpression());
                mementoSave.putOriginalPropertiesInCache(memento);
                cqlFilterLayerMapEvent.setLayerBean(layerSelected);
                GPHandlerManager.fireEvent(cqlFilterLayerMapEvent);
                treePanel.refresh(layerSelected);
            }
        });
        super.addButton(applyButton);
        Button closeButton = new Button("Close",
                new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                cqlFilterTabWidget.reset();
                hide();
            }
        });

        super.addButton(closeButton);
    }

    @Override
    public void show() {
        super.show();
        GPLayerTreeModel layerElement = (GPLayerTreeModel) treePanel.getSelectionModel().getSelectedItem();
        String cqlFilter = layerElement.getCqlFilter();
        if (cqlFilter != null) {
            this.cqlFilterTabWidget.setCQLValue(cqlFilter);
        }
    }

    @Override
    public void initSize() {
        super.setSize(WIDGET_WIDTH, WIDGET_HEIGHT);
    }

    @Override
    public void setWindowProperties() {
        super.setHeading(CQL_FILTER_HEADING);
        super.setLayout(new FitLayout());
        super.setModal(Boolean.TRUE);
    }
}
