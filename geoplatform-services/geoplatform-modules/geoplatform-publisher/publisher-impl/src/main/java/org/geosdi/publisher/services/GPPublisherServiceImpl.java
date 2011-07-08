//<editor-fold defaultstate="collapsed" desc="License">
/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-plartform.org
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
//</editor-fold>
package org.geosdi.publisher.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import org.springframework.transaction.annotation.Transactional;
import org.geosdi.publisher.exception.ResourceNotFoundFault;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.utils.NameLinkElem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipFile;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.geosdi.publisher.responce.PreviewElement;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.referencing.CRS;

/**
 * @author Luca Paolino - geoSDI
 *
 */
@Transactional // Give atomicity on WS methods
@WebService(endpointInterface = "org.geosdi.publisher.services.GPPublisherService")
public class GPPublisherServiceImpl  implements GPPublisherService{

    class InfoShape{
        String name;
        String epsg;
        String sld;
    }

    String RESTURL  = "http://localhost:8084/geoserver";
    String RESTUSER = "admin";
    String RESTPW   = "geoserver";
    GeoServerRESTPublisher publisher= null;
    GeoServerRESTReader reader = null;
    String tempDir = null;

    @Override
    public boolean publish(String workspace, String dataStoreName, String layerName) throws ResourceNotFoundFault,FileNotFoundException {
        String filename = tempDir+layerName+".zip";
        File file = new File(filename);
        boolean publish = false;
        if (file.exists()){
          publisher.unpublishFeatureType("previews", layerName, layerName);
          publisher.removeDatastore("previews", layerName);
            System.out.println(" **************** il file esiste. Layer name "+layerName);
            try {
                publish = publisher.publishShp("preview2", "data", layerName, file, "EPSG:4326");
   //             InfoShape info = getInfoFromCompressedShape(file);
            } catch(FileNotFoundException e) {
                System.out.println("\n ********** File not found");
            }
        }
//       
//
//        System.out.println("uno can read "+file.canRead());
//        System.out.println("workspace"+workspace);
//        System.out.println("dataStoreName"+dataStoreName);
//        System.out.println("layerName"+layerName);
//        System.out.println("Name: "+info.name+" SLD: "+info.sld+" EPSG: "+info.epsg);
        
        
        if (!publish) throw new ResourceNotFoundFault("Cannot publish "+layerName+" into "+workspace+":"+dataStoreName);
  //      publish = removeFromPreview(layerName);
/*            File filetemp = new File ("./repository/"+layerName+".shp");
            filetemp.delete();
            filetemp = new File ("./repository/"+layerName+".dbf");
            filetemp.delete();
            filetemp = new File ("./repository/"+layerName+".shx");
            filetemp.delete();
            filetemp = new File ("./repository/"+layerName+".prj");
            filetemp.delete();*/
        if (!publish) throw new ResourceNotFoundFault("Cannot unpublish from preview");
    //    publisher.removeDatastore("previews", layerName);
        return true;
    }

    @Override
    public boolean removeFromPreview(String dataStoreName) throws ResourceNotFoundFault {
        File file = new File(tempDir+dataStoreName+".zip");
        file.delete();
        publisher.unpublishFeatureType("previews", dataStoreName, dataStoreName);
        return true;
    }
  
    private String getURLPreviewByDataStoreName(String dataStoreName) throws ResourceNotFoundFault{
          RESTFeatureType featureType =  reader.getFeatureType(reader.getLayer(dataStoreName));
          double minX = featureType.getMinX();
          double maxX = featureType.getMaxX();
          double minY = featureType.getMinY();
          double maxY = featureType.getMaxY();
          return RESTURL+"/previews/wms?service=WMS&version=1.1.0&request=GetMap&layers=previews:"+dataStoreName+"&styles=&bbox="+minX+","+minY+","+maxX+","+maxY+"&width=512&height=499&srs="+featureType.getCRS()+"&format=image/png";
   }

    @Override
    public   List<PreviewElement> getPreviewDataStores() throws ResourceNotFoundFault {
          List<PreviewElement> listPreviews = new ArrayList<PreviewElement>();
          System.out.println("sono qui");
          RESTDataStoreList list = reader.getDatastores("previews");
          for (NameLinkElem element : list) {
              PreviewElement item = new PreviewElement(element.getName(), getURLPreviewByDataStoreName(element.getName()));
              listPreviews.add(item);
          }
          return listPreviews;
        

    }



    public GPPublisherServiceImpl() {
        publisher= new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        try {
            reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTPW);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GPPublisherServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        tempDir = System.getProperty("java.io.tmpdir");
   }

   private InfoShape getInfoFromCompressedShape(File file) {
        System.setProperty("org.geotools.referencing.forceXY", "true");
        String name ="";
        
       try {
            InfoShape info = new InfoShape();
            ZipFile zipSrc = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipSrc.entries();
            byte[] buf = new byte[1024];
            int n;
            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".shp")) {
                    info.name = entry.getName().substring(0, entry.getName().length()-4);
                    name = info.name;
                }
                InputStream zipinputstream = zipSrc.getInputStream(entry);
       //         System.out.println("entryname "+entryName);
                entryName = entryName.toLowerCase();
                FileOutputStream fileoutputstream = new FileOutputStream(tempDir+entryName);
                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
                    fileoutputstream.write(buf, 0, n);
                fileoutputstream.close();
                zipinputstream.close();
            }
            FileDataStore store = FileDataStoreFinder.getDataStore(new File(tempDir+info.name+".shp"));
            SimpleFeatureSource featureSource = store.getFeatureSource();
            String geomType = featureSource.getSchema().getGeometryDescriptor().getType().getName().toString();

        //    System.out.println("geometry descriptor"+geomType);
            if (geomType.equals("MultyPolygon")) info.sld="default_polygon";
            if (geomType.equals("PolyLine")) info.sld="default_polyline";
            if (geomType.equals("MultiPoint")) info.sld="default_point";
            Integer code  = CRS.lookupEpsgCode(featureSource.getSchema().getCoordinateReferenceSystem(), true);
            if (code!=null) {
                try {
                    info.epsg = "EPSG:"+code.toString();
                } catch(Exception e) { info.epsg = "EPSG:4326";}
                System.out.println("CODE "+code.toString());
            }
            else info.epsg = "EPSG:4326";
             zipSrc.close();
            return info;
       } catch(Exception e) {
           e.printStackTrace();
           return null;
       }

   }

    @Override
    public String uploadZIPInPreview(File file) throws ResourceNotFoundFault {
        InfoShape info = getInfoFromCompressedShape(file);
        if (info==null) throw new ResourceNotFoundFault("The ZIP archive does not contain a shp file");
        //GeoServerRESTPublisher publisher= new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
         System.out.println("tre");
         try {
            List<String> workspaces = reader.getWorkspaceNames();
            // check if the previews workspace exist, create it if not
            if (!workspaces.contains("previews"))
                    publisher.createWorkspace("previews");
            // check if the layer already exists, if not the service returns an exception

             //publish the shape in the previews workspace
            // calculate the PNG URL to return
            String urlPNGPreview = "error";
            
            // create the <layername>.zip file
            
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(tempDir+info.name+".zip");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            
             RESTDataStore dataStore = reader.getDatastore("previews", info.name);
     //      if (dataStore!=null) throw new ResourceNotFoundFault(info.name+" layer already exists");
            if (dataStore==null) {
                System.out.println("\n ********** Il layer in preview non esiste, lo pubblico in preview");
                File temp = new File(tempDir+info.name+".zip");
                boolean published = publisher.publishShp("previews", info.name, info.name, temp, info.epsg);
             }
            urlPNGPreview = getURLPreviewByDataStoreName(info.name);
            return urlPNGPreview;

    }
        catch (FileNotFoundException ex) {
            throw new ResourceNotFoundFault("File not found Exception");
        }
        catch (IOException ex) {
            throw new ResourceNotFoundFault("IO Exception");
        }
    }

    private ZipOutputStream compress(ZipOutputStream out,File inFile) throws IOException {
        FileInputStream inShpFil = new FileInputStream(inFile); // Stream to read file
        ZipEntry entryShp = new ZipEntry(inFile.getName()); // Make a ZipEntry
        out.putNextEntry(entryShp); // Store entry
        byte[] buffer = new byte[4096]; // Create a buffer for copying
        int bytesRead;
        while ((bytesRead = inShpFil.read(buffer)) != -1) out.write(buffer, 0, bytesRead);
        inShpFil.close();
        return out;
   }

    @Override
    public String uploadShapeInPreview(File shpFile, File dbfFile, File shxFile, File prjFile) throws ResourceNotFoundFault {
   //     System.out.println("uploadShape");
        
        String name = shpFile.getName().substring(0, shpFile.getName().length() - 4);
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempDir+"temp.zip"));
            System.out.println("due");
            out = compress(out, shpFile);
            out = compress(out, dbfFile);
            out = compress(out, shxFile);
            out = compress(out, prjFile);
            out.close();
            System.out.println("due");
            File compressedFile = new File (tempDir+"temp.zip");
System.out.println("due");
            String url = uploadZIPInPreview(compressedFile);
System.out.println("due");
       //     compressedFile.delete();
            return url;
        }
        catch (ResourceNotFoundFault e) {
         //   ex.printStackTrace();
             throw new ResourceNotFoundFault(name+" layer already exists");
        }
        catch (Exception ex) {
         //   ex.printStackTrace();
            throw new ResourceNotFoundFault("Cannot create the zip temp file, some files are missing or are malformed"+ex.getMessage());
            
        }



    }


}