package com.weblieu.findtrue.repositry;

import com.weblieu.findtrue.model.AddShortList;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.CategoryDetails;
import com.weblieu.findtrue.model.CategoryProfile;
import com.weblieu.findtrue.model.CityModel;
import com.weblieu.findtrue.model.EditProfileUpdate;
import com.weblieu.findtrue.model.GetAllGallery;
import com.weblieu.findtrue.model.GetFAQ;
import com.weblieu.findtrue.model.GetPackage;
import com.weblieu.findtrue.model.GetUserLead;
import com.weblieu.findtrue.model.Login;
import com.weblieu.findtrue.model.Res;
import com.weblieu.findtrue.model.SendEnquiry;
import com.weblieu.findtrue.model.UserRegistration;
import com.weblieu.findtrue.model.VendorLogin;
import com.weblieu.findtrue.profile.GetProfile;
import com.weblieu.findtrue.profile.ImageUpdate;
import com.weblieu.findtrue.profile.UpdateProfile;
import com.weblieu.findtrue.search.SearchCategory;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    //Registration ////user(production) //api uses only for(test) https://www.truefind.in/api/user
    @POST("api/user")
    Call<UserRegistration> userRegistration(@Body UserRegistration userRegistration);


    ////Login-------------------//lOGIN Test(api/login) and production filed (user/login)
    @FormUrlEncoded
    @POST("api/user/login")
    Call<Login> userLogin(@Field("email") String email, @Field("password") String password);

    //call api list of category
    @GET("api/category")

    Call<List<Category>> getCategory();

    //call api list of category detailsget_product_list_by_city
    @FormUrlEncoded
    @POST("api/get_product_list_by_city")
    Call<CategoryDetails> getCategoryDetails(@Field("catid") String catid, @Field("cityid") String cityid);

    @FormUrlEncoded
    @POST("api/save_vendor_msg2")
    Call<ResponseRequest> sendEnquiry(@Field("vndrbox") String vndrbox, @Field("category_id") String category_id, @Field("city_id") String city_id, @Field("areapinid") String areapinid, @Field("name") String name, @Field("email") String email, @Field("mobile_country_code") String mobile_country_code, @Field("contactno") String contactno, @Field("bookingdate") String bookingdate, @Field("whatsapp_message_status") String whatsapp_message_status, @Field("ipaddress") String ipaddress);

    @FormUrlEncoded
    @POST("api/product_serch_list")
    Call<SearchCategory> searchProduct(
            @Field("catid") String catid,
             @Field("cityid") String cityid
    );

    @FormUrlEncoded
    @POST("api/get_profile")
    Call<CategoryProfile> getCategoryProfile(@Field("vid") String vid);

    @GET("api/get_city_list")
    Call<CityModel> getCity();

    @FormUrlEncoded
    @POST("api/user/user_details")
    Call<GetProfile> getUserProfile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/user/user_profile_update")
    Call<UpdateProfile> updateProfile(@Field("fname") String fname, @Field("lname") String lname, @Field("email") String email, @Field("gender") String gender, @Field("user_id") String user_id);


    @Multipart
    @POST("api/user/user_profile_pic_update")
    Call<ResponseBody> uploadProfilePic(@Part("user_id") RequestBody user_id, @Part MultipartBody.Part image, @Part("img_url") RequestBody img_url);

    @FormUrlEncoded
    @POST("api/user/user_profile_pass_update")
    Call<UpdateProfile> updatePassword(@Field("user_id") String user_id, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user/get_vendor_save_list")
    Call<AddShortList> getShortList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/user/add_wishlist")
    Call<ResponseRequest> addWishList(@Field("userid") String userid, @Field("vendorid") String vendorid, @Field("categoryid") String categoryid);

    @FormUrlEncoded
    @POST("api/vendor_registration")
    Call<ResponseRequest> vendorRegister(@Field("vendor_name") String vendor_name, @Field("emailid") String emailid, @Field("business_name") String business_name, @Field("contact_number") String contact_number, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/vendor_login")
    Call<VendorLogin> vendorLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/get_vendor_info")
    Call<EditProfileUpdate> getVendorEditProfile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/vendor_profile_update")
    Call<ResponseRequest> updateVandorProfile(@Field("user_id") String user_id, @Field("business_name") String business_name, @Field("vendor_name") String vendor_name, @Field("city") String city, @Field("localarea") String localarea, @Field("address") String address,
                                              @Field("pincode") String pincode, @Field("country") String country, @Field("website") String website, @Field("contactno") String contactno, @Field("landline_code") String landline_code, @Field("landline_no") String landline_no, @Field("established") String established,
                                              @Field("accepts") String accepts, @Field("officehours") String officehours, @Field("category") String category);


    @Multipart
    @POST("api/vendor_profile_pic_update")
    Call<ResponseRequest> updateVendorPic(@Part("user_id") RequestBody user_id, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/vendor_save_faq")
    Call<ResponseRequest> updateFAQ(@Field("question") String question, @Field("answer") String answer, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/get_vendor_save_faq")
    Call<GetFAQ> getVendorFAQ(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/vendor/update_description")
    Call<ResponseRequest> submitDescription(@Field("user_id") String user_id, @Field("descrip") String descrip);

    @FormUrlEncoded
    @POST("api/vendor/add_youtube_link")
    Call<ResponseRequest> uploadYouTubeUrl(@Field("user_id") String user_id, @Field("vfile") String vfile);

    @Multipart
    @POST("api/vendor/add_gallery")
    Call<ResponseBody> uploadGallery(@Part("user_id") RequestBody user_id, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/vendor/get_vendor_gallery")
    Call<GetAllGallery> getAllGalery(@Field("user_id") String user_id);


    @Multipart
    @POST("user/Apis/hiring")
    Call<ResponseBody> submitHiring(@Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("mother_name") RequestBody mother_name, @Part("father_name") RequestBody father_name,
                                    @Part("date") RequestBody date, @Part("address") RequestBody address, @Part("adar_number") RequestBody adar_number, @Part("email") RequestBody email,
                                    @Part("contact_number") RequestBody contact_number, @Part("fees") RequestBody fees, @Part("ten_school") RequestBody ten_school, @Part("ten_percentage") RequestBody ten_percentage,
                                    @Part MultipartBody.Part ten_image, @Part("twelve_school") RequestBody twelve_school, @Part("twelve_percentage") RequestBody twelve_percentage,
                                    @Part MultipartBody.Part twelve_image, @Part("ug_school") RequestBody ug_school, @Part("ug_percentage") RequestBody ug_percentage, @Part MultipartBody.Part ug_image,
                                    @Part MultipartBody.Part adar_card, @Part("company_name") RequestBody company_name, @Part("job_profile") RequestBody job_profile, @Part("experince") RequestBody experince,
                                    @Part("position") RequestBody position, @Part("fax") RequestBody fax, @Part("relocate") RequestBody relocate, @Part("last_company") RequestBody last_company,
                                    @Part MultipartBody.Part photograph, @Part MultipartBody.Part signature, @Part("comment") RequestBody comment, @Part("orderId") RequestBody orderId, @Part("choosepost") RequestBody choosepost);


    @FormUrlEncoded
    @POST("cashfree_token_api.php")
    Call<Res> getToken(@Field("orderAmount") String orderAmount, @Field("orderId") String orderId);

    @FormUrlEncoded
    @POST("vendor/Apis/get_user_leads")
    Call<GetUserLead> getUserLeads(@Field("user_id") String user_id);

    @GET("vendor/Apis/get_package")
    Call<GetPackage> getPackage();

}
