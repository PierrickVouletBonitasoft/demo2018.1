package com.company.rest.api;

import groovy.json.JsonBuilder

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.http.HttpHeaders
import org.bonitasoft.engine.expression.Expression
import org.bonitasoft.engine.expression.ExpressionBuilder
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController

class Index implements RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Index.class)

    @Override
    RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
        // To retrieve query parameters use the request.getParameter(..) method.
        // Be careful, parameter values are always returned as String values

        // Retrieve messageCaseId parameter
        def messageCaseId = request.getParameter "messageCaseId"
		Long caseid = Long.valueOf(request.getParameter("messageCaseId"))
		
		def idParm = request.getParameter "id"
		Long id = Long.valueOf(request.getParameter("id"))
		
		def message = request.getParameter "message"
		
		def target = request.getParameter "target"
        
		if (messageCaseId == null) {
            return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter messageCaseId is missing"}""")
        }
		
		if (idParm == null) {
			return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter id is missing"}""")
        }
		
		if (message == null) {
			return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter message is missing"}""")
        }
		
		if (target == null) {
			return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter target is missing"}""")
        }
		
		Map correlations = new HashMap();
		Expression key = new ExpressionBuilder().createConstantStringExpression("messageCaseId")
		Expression value = new ExpressionBuilder().createConstantLongExpression(caseid)
		correlations.put(key , value)
		
		// id of user to assign task to
		Map messageContent = new HashMap();
		key = new ExpressionBuilder().createConstantStringExpression("id")
		value = new ExpressionBuilder().createConstantLongExpression(id)
		messageContent.put(key , value)
		
		Expression targetProcess = new ExpressionBuilder().createConstantStringExpression("Initial approval");
		Expression targetFlowNode = new ExpressionBuilder().createConstantStringExpression(target);
		
        context.apiClient.processAPI.sendMessage(message, targetProcess, targetFlowNode, messageContent, correlations);
        
        // Prepare the result
        def result = [ "messageCaseId" : messageCaseId,
					   "message" : message,
					   "targetProcess" : targetProcess,	
					   "targetFlowNode" : targetFlowNode
					 ]

        // Send the result as a JSON representation
        // You may use buildPagedResponse if your result is multiple
        return buildResponse(responseBuilder, HttpServletResponse.SC_OK, new JsonBuilder(result).toPrettyString())
    }

    /**
     * Build an HTTP response.
     *
     * @param  responseBuilder the Rest API response builder
     * @param  httpStatus the status of the response
     * @param  body the response body
     * @return a RestAPIResponse
     */
    RestApiResponse buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
        return responseBuilder.with {
            withResponseStatus(httpStatus)
            withResponse(body)
            build()
        }
    }

    /**
     * Returns a paged result like Bonita BPM REST APIs.
     * Build a response with content-range data in the HTTP header.
     *
     * @param  responseBuilder the Rest API response builder
     * @param  body the response body
     * @param  p the page index
     * @param  c the number of result per page
     * @param  total the total number of results
     * @return a RestAPIResponse
     */
    RestApiResponse buildPagedResponse(RestApiResponseBuilder responseBuilder, Serializable body, int p, int c, long total) {
        return responseBuilder.with {
            withAdditionalHeader(HttpHeaders.CONTENT_RANGE,"$p-$c/$total");
            withResponse(body)
            build()
        }
    }

    /**
     * Load a property file into a java.util.Properties
     */
    Properties loadProperties(String fileName, ResourceProvider resourceProvider) {
        Properties props = new Properties()
        resourceProvider.getResourceAsStream(fileName).withStream { InputStream s ->
            props.load s
        }
        props
    }

}
