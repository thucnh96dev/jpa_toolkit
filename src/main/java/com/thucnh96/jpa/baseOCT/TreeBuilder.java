package com.thucnh96.jpa.baseOCT;

import com.thucnh96.jpa.baseOCT.ntree.Node;
import org.springframework.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import java.util.ArrayList;
import java.util.HashMap;

public class TreeBuilder<T> {

    /**
     * example "id","pid","ma"
     * @param initialList
     * @param idProp
     * @param pidProp
     * @param codeProp
     * @return HashMap
     * @throws Exception
     */
    public  HashMap<String, Node<T>> buildTree(ArrayList<T> initialList, String idProp, String pidProp,String codeProp) throws Exception {
        String idCode = (null != codeProp) ? codeProp : idProp;
        HashMap<String, Node<T>> mapNode = new HashMap<>();
        HashMap<String, Node<T>> idMap = new HashMap<>();
        for (int i = 0; i < initialList.size(); i++) {
            T itemRaw = initialList.get(i);
            String key = BeanUtils.getProperty(itemRaw, idCode);
            String keyId = BeanUtils.getProperty(itemRaw, idProp);
            String parentId = BeanUtils.getProperty(itemRaw, pidProp);
            if (!StringUtils.isEmpty(parentId)) {
                if (idMap.containsKey(parentId)) {
                    Node<T> itemTree = new Node<>(itemRaw, idMap.get(parentId));
                    mapNode.put(key, itemTree);
                    idMap.put(keyId, itemTree);
                }
            } else {
                Node<T> itemTree = new Node<>(itemRaw);
                mapNode.put(key, itemTree);
                idMap.put(keyId, itemTree);
            }
        }
        return mapNode;
    }
}
