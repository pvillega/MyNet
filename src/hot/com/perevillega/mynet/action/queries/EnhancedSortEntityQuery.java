package com.perevillega.mynet.action.queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.jboss.seam.framework.EntityQuery;

public class EnhancedSortEntityQuery<E> extends EntityQuery<E> {
	
    // Copied from 'Query' because has been declared private in that class !!
    private static final Pattern ORDER_COLUMN_PATTERN = Pattern.compile("^\\w+(\\.\\w+)*$");
    private static final String DIR_ASC = "asc";
    private static final String DIR_DESC = "desc";
	
    /*************************************************************************/
    /* EnhancedSortEntityQuery configuration params                          */
    /*************************************************************************/
	
    /** (De)/Activate listing order tracking feature */
    private boolean activePrevListingOrderTracking = true;  // Default: active

    /** @return true if listing order tracking feature is active */
    public boolean isActivePrevListingOrderTracking() {
        return activePrevListingOrderTracking;
    }

    /**
     * (De)/Activate listing order tracking feature
     * @param activePrevListingOrderTracking 'true' to activate order tracking
     */
    public void setActivePreviousOrderTracking(boolean activePrevListingOrderTracking) {
        this.activePrevListingOrderTracking = activePrevListingOrderTracking;
    }
	

    /** 
     * Establish maximun number of properties that must be remembered for order tracking.
     * Only meaningless when order tracking has been enabled.
     */
    private int orderTrackingDeep = 0;  // Default: no limit

    /** @return maximun number of properties remenbered for order tracking */
    public int getOrderTrackingDeep() { return orderTrackingDeep; }
    /**
     * Sets maximun number of properties remmembered for order tracking
     * Only meaninfull if order tracking has been enabled
     * @param orderTrackingDeep nof properties remembered for order tracking. 0 for no limit. Default 0
     */
    public void setOrderTrackingDeep(int orderTrackingDeep) {
        this.orderTrackingDeep = orderTrackingDeep;
    }

	
	
    /** 
     * Save the 'propertyPath' param set in 'xxxList.xhtml' in the columns header sections for the user clicked column.
     * Format: propertyPath [ASC/DESC][, propertyPath [ASC/DESC]].
     * If no direction (ASC/DESC) is included for a property, deafult ASC direction is used
     * Updated from 'sort.xhtml' with the contens of 'propertyPath' param set in 'xxxList.xhtml' for the user clicked column. 
     * Note: Must be updated with exactly the same string used in 'xxxList.xhtml' because 'sort.xhtml' test its equality to 
     * determine wich column that has been used to sort the listing and to apped the up or down arrow in its header label
     */
	
    // Example 'xxxList.xhtml' column definition code:
    // 
    //    <h:column>
    //	      <f:facet name="header">
    //            <ui:include src="/layout/sort.xhtml">
    //	              <ui:param name="entityList" value="#{departmentList}"/>
    //                <ui:param name="propertyLabel" value="Manager"/>
    //                <ui:param name="propertyPath" value="department.manager.firstName, department.manager.lastName"/>
    //            </ui:include>
    //        </f:facet>
    //        <h:outputText value="#{_department.manager.firstName} #{_department.manager.lastName}"
    //    </h:column>

    private String orderColumns;
    /** Returns user clicked column header 'propertyPath' param */
    public String getOrderColumns() { return orderColumns; }
    /** Called from the view to set user clicked column header 'propertyPath' param */
    public void setOrderColumns(String orderColumns) { this.orderColumns = orderColumns; }
	
    /** Called from the view to establish order column direction */
    @Override
    public void setOrderDirection(String orderDirection) {
        super.setOrderDirection(orderDirection);
        setOrder(processQueryOrder());
    }
	
    // Copied from 'Query' because has been declared private in that class !!
    private String sanitizeOrderColumn(String columnName) {
        if (columnName == null || columnName.trim().length() == 0) {
            return null;
        } else if (ORDER_COLUMN_PATTERN.matcher(columnName).find()) {
            return columnName;
        } else {
            throw new IllegalArgumentException("invalid order column (\"" + columnName + "\" must match the regular expression \"" + ORDER_COLUMN_PATTERN + "\")");
        }
    }
	
    // Copied from 'Query' because has been declared private in that class !!
    private String sanitizeOrderDirection(String direction) {
        if (direction == null || direction.length()==0) {
            return null;
        } else if (direction.equalsIgnoreCase(DIR_ASC)) {
            return DIR_ASC;
        } else if (direction.equalsIgnoreCase(DIR_DESC)) {
            return DIR_DESC;
        } else {
            throw new IllegalArgumentException("invalid order direction");
        }
    }


    /** Sort Directions enumeration with its labels */
    private enum SortDirection {
        ASC(DIR_ASC),
        DESC(DIR_DESC);
        SortDirection(String label) { this.label = label; }
        private String label;
        public String getLabel() { return label; }
    }
	
    /** Auxiliary class */
    private class SortParam {
        public String propertyPath;
        public SortDirection dir;
        public SortParam(String column, SortDirection dir) {
            this.propertyPath = column; this.dir = dir;
        }
    };
	
    /** 
     * Parses 'sanitizedOrderByParamsStr' and fills an ArrayList with {propertyPath, "asc"/"desc"} pairs 
     * for each property used in listing sort.
     * @param sanitizedOrderByParamsStr Format:
     *     propertyPath "asc"/"desc", propertyPath "asc"/"desc", ...... propertyPath "asc"/"desc"
     * Ex: "department.manager.firstName asc, department.manager.lastName asc"
     * @return ArrayList initialized with the info extracted from 'sanitizedOrderByParamStr'
     */
    private List<SortParam> initSortParamsArray(String sanitizedOrderByParamsStr) {
        List<SortParam> sortParamsList = new ArrayList<SortParam>();
        StringTokenizer tokens = new StringTokenizer(sanitizedOrderByParamsStr, ",");
        String token;
        String propertyPath;
        String sortDirectionStr;
        while (tokens.hasMoreTokens()) {
            token = tokens.nextToken().trim();
            propertyPath = token.substring(0,token.lastIndexOf(' ')).trim(); 
            sortDirectionStr = token.substring(token.lastIndexOf(' ')).trim();
            sortParamsList.add(new SortParam(
                propertyPath, 
                sortDirectionStr.equals(DIR_ASC) ? SortDirection.ASC : SortDirection.DESC)
            );
        }
        return sortParamsList;
    }
	
    /**
     * Parses 'sanitizedOrderByParamsStr' and fills a Map with key='propertyPath', value="ASC"/"DESC". 
     * @param sanitizedOrderByParamsStr Format: 
     *     propertyPath "asc"/"desc", propertyPath "asc"/"desc", ...... propertyPath "asc"/"desc"
     * Ex: "department.manager.firstName asc, department.manager.lastName asc"
     * @return HashMap with keys an values extracted from 'sanitizedOrderByParamsStr'
     */
    private Map<String, SortDirection> initSortParamsMap(String sanitizedOrderByParamsStr) {
        Map<String, SortDirection> sortParamsMap = new HashMap<String, SortDirection>(0);
        StringTokenizer tokens = new StringTokenizer(sanitizedOrderByParamsStr, ",");
        String token;
        String propertyPath;
        String sortDirectionStr;
        while (tokens.hasMoreTokens()) {
            token = tokens.nextToken().trim();
            propertyPath = token.substring(0,token.lastIndexOf(' ')).trim(); 
            sortDirectionStr = token.substring(token.lastIndexOf(' ')).trim();
            sortParamsMap.put(
                propertyPath, 
                sortDirectionStr.equals(DIR_ASC) ? SortDirection.ASC : SortDirection.DESC
            );
        }
        return sortParamsMap;
    }
	
		
    /** Previous listing sanitized ORDER BY parameters string */
    private String prevSanitizedOrderByParamsStr = new String();
    public String getPrevSanitizedOrderByParamsStr() { return prevSanitizedOrderByParamsStr; }
    public void setPrevSanitizedOrderByParamsStr(String prevSanitizedOrderByParamsStr) {
        this.prevSanitizedOrderByParamsStr = prevSanitizedOrderByParamsStr;
    }

    private String processQueryOrder() {

        if (getOrderColumns() == null) return null;

        StringTokenizer tokens = new StringTokenizer(getOrderColumns(), ",");
        String propertyPath;
        String propertyDir;
        String token;
        String orderByParameters = new String();
 
        while (tokens.hasMoreTokens()) {
			
            token = tokens.nextToken().trim();
			
            int dirIndex = token.lastIndexOf(' ');  // Direction, if included, must be preceded by ' '
			
            if (dirIndex != -1) {  // Direction included in view
                propertyPath = sanitizeOrderColumn(token.substring(0,token.lastIndexOf(' ')));
                propertyDir = sanitizeOrderDirection(token.substring(token.lastIndexOf(' ')).trim());
                if (propertyDir == null) propertyDir = DIR_ASC;  // Por defecto, ascendente
            }
            else {  // Direction not included in view
                propertyPath = sanitizeOrderColumn(token);
                propertyDir = DIR_ASC;  // Default: ASC
            }
			
            if (getOrderDirection().equals(DIR_DESC)) {
                // Reverse property direction
                propertyDir = (propertyDir.equals(DIR_ASC)) ? DIR_DESC : DIR_ASC;
            }

            orderByParameters +=  propertyPath + " " + propertyDir;
			
            if (tokens.hasMoreTokens()) orderByParameters += ", ";
        }
		
        return orderByParameters;
    }
	
    /** Gets query results */
    @Override
    public List<E> getResultList() {

        // Force 'Query' to use 'order' instead of 'orderColumn' to process query ORDER BY clause parameters
        setOrderColumn("");
		
        String currentSanitizedOrderByParamsStr = getOrder();
 		
        if (
            !isActivePrevListingOrderTracking() || 
            getOrder() == null ||
            prevSanitizedOrderByParamsStr.equals(getOrder())
        ) {
            return super.getResultList();
        }
		
        List<SortParam> prevSortParams = initSortParamsArray(prevSanitizedOrderByParamsStr);
        Map<String, SortDirection> currentSortParams = initSortParamsMap(currentSanitizedOrderByParamsStr);
		
        String prevPropertyPath;
        SortDirection prevSortDir;
        for (int i = 0; i < prevSortParams.size(); i++) {
            if ((getOrderTrackingDeep() != 0) && (currentSortParams.size() + i >= getOrderTrackingDeep())) break;
            prevPropertyPath = prevSortParams.get(i).propertyPath;
            prevSortDir = prevSortParams.get(i).dir;
            if (!currentSortParams.containsKey(prevPropertyPath)) {
                currentSanitizedOrderByParamsStr += ", " + prevPropertyPath + " " + prevSortDir.getLabel();
            }
        }
		
        setOrder(currentSanitizedOrderByParamsStr);
        prevSanitizedOrderByParamsStr = currentSanitizedOrderByParamsStr;
	
        return super.getResultList();
    }
    
    /** Bypasses the order customisations to return the original result set, for actions on the resultset like delete an item */
    public List<E> getStaticResultList() {
    	return super.getResultList();
    }
}

