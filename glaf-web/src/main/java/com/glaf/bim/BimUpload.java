package com.glaf.bim;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BimUpload {
	public static String fileName=null;
	
	public static String client_id = "sWSQ59iBg1FQqLvLRcYwNEyuCBomL3xj" ;
	public static String client_secret = "C4fe9fe9df9cc47c" ;
	public static String urlBase = "https://developer.api.autodesk.com/" ;
	
	/**
	 * 主方法
	 * @param file:上传的文件
	 * @return
	 */
	public JSONObject transformMain(File file){
		return baseTransformMain(buildFileEntity(file));
	}
	
	public JSONObject transformMain(InputStream is,String name){
		fileName=name;
		return baseTransformMain(buildInputStreamEntity(is));
	}
	
	
	public JSONObject baseTransformMain(RequestEntity entity){
		/*获取令牌*/
		String tocken=getToken();
		
		/**
		 * 上传文件
		 */
		String objectId=uploadFillToBucket(tocken,entity);
		
		/**
		 * base64 Encode
		 */
		String objectId_Base64=base64Encode(objectId);
		
		/**
		 * 转化
		 */
		return sourceTransformToSVF(tocken,objectId_Base64);
	}
	
	public String base64Encode(String objectId){
		byte[] encodeBase64=null;
		try {
			encodeBase64 = Base64.encodeBase64(objectId.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return new String(encodeBase64);
	}
	
	
	/**
	 * 
	 * @param client_id:创建App时生产的client_id
	 * @param client_secret：创建App时生产的client_secret
	 * @param create：你是要生成那种类型的令牌
	 * @return
	 */
	public String getToken() {
		String url = urlBase+"authentication/v1/authenticate";
//		create="data:read";//create="bucket:create";


		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		
		postMethod.setParameter("client_id", client_id);
		postMethod.setParameter("client_secret", client_secret);
		postMethod.setParameter("grant_type", "client_credentials");
		postMethod.setParameter("scope", "data:read data:write");

		JSONObject obj = null;

		try {
			httpClient.executeMethod(postMethod);
			Header[] headers = postMethod.getResponseHeaders();
			int statusCode = postMethod.getStatusCode();
			System.out.println("code:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(postMethod.getResponseBodyAsString().getBytes("GBK"));
			obj = new JSONObject();
			obj = JSON.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
		return (String) obj.get("access_token");
	}

	
	
	/**
	 * 创建bucket容器
	 * @param bucketName:给容器的名字
	 * @param tocken:令牌（创建容器的时候需要令牌）
	 */

	public void getBucket(String bucketName, String tocken) {

		String url = urlBase+"oss/v2/buckets";

		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/json");
		postMethod.addRequestHeader("Authorization", "Bearer " + tocken);

		postMethod.setParameter("bucketKey", bucketName);
		postMethod.setParameter("policyKey", "persistent");

		try {
			httpClient.executeMethod(postMethod);
			Header[] headers = postMethod.getResponseHeaders();
			int statusCode = postMethod.getStatusCode();
			System.out.println("code:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(postMethod.getResponseBodyAsString().getBytes("GBK"));

			// 打印输出结果
			System.out.println(result);
			postMethod.releaseConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private RequestEntity buildInputStreamEntity(InputStream is){
		return new InputStreamRequestEntity(is);
	}
	
	private RequestEntity buildFileEntity(File file) {
		return new FileRequestEntity(file, null);
	}

	/**
	 * 将文件上传到bucket容器中
	 * @param tocken：令牌
	 * @param file：需要上传的文件
	 * @return
	 */

	public String uploadFillToBucket(String tocken, RequestEntity entity) {
		String url = urlBase+"oss/v2/buckets/chinaiss12_bucket/objects/"+fileName;
		PutMethod filePost = new PutMethod(url);
		JSONObject obj=null;
		try {
			filePost.addRequestHeader("Content-Type", "application/octet-stream");
			filePost.addRequestHeader("Authorization", "Bearer "+tocken);
			
			
			filePost.setRequestEntity(entity);
			
			
			HttpClient client = new HttpClient();
			Header[] requestHeaders = filePost.getRequestHeaders();
			
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			
			int status = client.executeMethod(filePost);
			
			try {
				Header[] headers = filePost.getResponseHeaders();
				int statusCode = filePost.getStatusCode();
				System.out.println("code:" + statusCode);
				for (Header h : headers) {
					System.out.println(h.toString());
				}
				String result = new String(filePost.getResponseBodyAsString().getBytes("GBK"));

				obj = new JSONObject();
				obj = JSON.parseObject(result);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return (String)obj.get("objectId");
	}
	
	
	/**
	 * 
	 * @param tocken：令牌
	 * @param objectId_Base64：加密后的urn
	 * @return
	 */
	public JSONObject sourceTransformToSVF(String tocken,String objectId_Base64){

		String url = urlBase+"modelderivative/v2/designdata/job";
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/json");
		postMethod.addRequestHeader("Authorization", "Bearer " + tocken);
		
		String buffer="{\"input\":{\"urn\":\""+objectId_Base64+"\"},\"output\":{\"formats\":[{\"type\":\"svf\",\"views\":[\"2d\",\"3d\"]}]}}";
		postMethod.setRequestBody(buffer);
				
		JSONObject obj=null;
		try {
			httpClient.executeMethod(postMethod);
			Header[] headers = postMethod.getResponseHeaders();
			int statusCode = postMethod.getStatusCode();
			System.out.println("code:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(postMethod.getResponseBodyAsString().getBytes("GBK"));

			obj = new JSONObject();
			obj = JSON.parseObject(result);
			

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
		return obj;
	}
	

}
