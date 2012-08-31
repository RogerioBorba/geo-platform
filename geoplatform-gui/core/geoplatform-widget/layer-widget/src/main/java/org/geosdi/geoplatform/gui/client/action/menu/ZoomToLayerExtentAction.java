/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geosdi.geoplatform.gui.client.action.menu;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import org.geosdi.geoplatform.gui.action.menu.MenuAction;
import org.geosdi.geoplatform.gui.client.model.RasterTreeNode;
import org.geosdi.geoplatform.gui.configuration.map.puregwt.MapHandlerManager;
import org.geosdi.geoplatform.gui.configuration.map.puregwt.event.ZoomToExtendsEvent;
import org.geosdi.geoplatform.gui.model.tree.GPBeanTreeModel;

/**
 *
 * @author Francesco Izzi - CNR IMAA geoSDI Group
 * @mail francesco.izzi@geosdi.org
 */
public class ZoomToLayerExtentAction extends MenuAction {

    private TreePanel treePanel;
    private ZoomToExtendsEvent zoomToExtendsEvent = new ZoomToExtendsEvent();

    public ZoomToLayerExtentAction(TreePanel treePanel) {
        super("ZoomToLayerExtent");
        this.treePanel = treePanel;
    }

    @Override
    public void componentSelected(MenuEvent ce) {
        GPBeanTreeModel item = (GPBeanTreeModel) this.treePanel.getSelectionModel().getSelectedItem();

        if (item instanceof RasterTreeNode) {
            zoomToExtendsEvent.setBbox(((RasterTreeNode) item).getBbox());
            zoomToExtendsEvent.setCrs(((RasterTreeNode) item).getCrs());
            MapHandlerManager.fireEvent(zoomToExtendsEvent);
        }
    }
}