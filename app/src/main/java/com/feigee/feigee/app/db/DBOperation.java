package com.feigee.feigee.app.db;

import android.content.Context;
import com.feigee.feigee.app.MainApplication;
import com.feigee.feigee.app.entity.TodoList;

import java.sql.SQLException;

public class DBOperation {
    private static DBOperation singleInstance;

    public synchronized static DBOperation getInstance(Context context) {
        if (singleInstance == null) {
            singleInstance = new DBOperation();
        }
        return singleInstance;
    }

    public void saveTodoMsg(TodoList entity) throws SQLException {
        DatabaseHelper.getInstance(MainApplication.getContext()).getTodoListDao().createOrUpdate(entity);
    }
//
//    public List<DailyManageEntity> getDailyMsg(String id) {
//        List<DailyManageEntity> list = new ArrayList<DailyManageEntity>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("messageFrom", id);
//        try {
//            list =DatabaseHelper.getInstance().getChatMsgsDao().queryForFieldValues(map);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public void saveUserMsg(UserInfoEntity entity) {
//        try {
//            DatabaseHelper.getInstance().getUserInfosDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public UserInfoEntity getUserInfo(String id){
//        List<UserInfoEntity> list = new ArrayList<UserInfoEntity>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("_id", id);
//        UserInfoEntity user = new UserInfoEntity();
//        try {
//            list = DatabaseHelper.getInstance().getUserInfosDao().queryForFieldValuesArgs(map);
//            if(list.size() == 1){
//                user = list.get(0);
//                if(user.getImg() < 0){
//                    user.setImg(R.drawable.new_default);
//                }
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return user;
//    }
//
//    public List getComprehensiveInfo(int type){
//        List infoList = new ArrayList();
//        try {
//            infoList = Constant.comprehensiveDaoList.get(type).queryForAll();
//        } catch (SQLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        return infoList;
//    }
//
//    public void delComprehensiveInfo(int type,Object entity){
//        try {
//            Constant.comprehensiveDaoList.get(type).delete(entity);
//        } catch (SQLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//    }
//
//    public void saveComplaintInfoMsg(ComplaintInfoEntity entity){
//        try {
//            DatabaseHelper.getInstance().getComplaintInfoDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public void saveForeignPeopleInfoMsg(ForeignPeopleEntity entity){
//        try {
//            DatabaseHelper.getInstance().getForeignPeopleInfoDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    //	public void close() {
////		if (db != null)
////			DatabaseHelper.getInstance().close();
////	}
//    public void savePlaceInfoMsg(PlaceInfoEntity entity){
//        try {
//            DatabaseHelper.getInstance().getPlaceInfoDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public void saveCrazyPersonMsg(CrazyPersonEntity entity){
//        try {
//            DatabaseHelper.getInstance().getCrazyPersonDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public void saveCommunityInfoMsg(CommunityEntity entity){
//        try {
//            DatabaseHelper.getInstance().getCommunityInfoDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public void saveInspectInfoMsg(InspectEntity entity){
//        try {
//            DatabaseHelper.getInstance().getInspectInfoDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public int saveContradictionInfoMsg(ContradictionEntity entity){
//        try {
//            DatabaseHelper.getInstance().getContradictionInfoDao().createOrUpdate(entity);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return entity.get_id();
//    }
//
//    public ContradictionEntity getContradictionInfoMsg(int id){
//        ContradictionEntity contradictionEntity = null;
//        try {
//            contradictionEntity = DatabaseHelper.getInstance().getContradictionInfoDao().queryForId(id);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return contradictionEntity;
//    }
//
//    public List getCaseReportInfoMsg(int type){
//        List infoList = new ArrayList();
//        try {
//            infoList = Constant.contradictionDaoList.get(type).queryForAll();
//        } catch (SQLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        return infoList;
//    }
//
//    public void saveImageInfoMsg(List<ImageEntity> imageList){
//        List<ImageEntity> list = imageList;
//        for(ImageEntity image : list){
//            try {
//                DatabaseHelper.getInstance().getImageInfoDao().createOrUpdate(image);
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public List<ImageEntity> getImageInfoMsg(int reportType){
//        List<ImageEntity> imageList = new ArrayList<ImageEntity>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("userID", Constant.getUser().get_id());
//        map.put("reportType", reportType);
////		UserInfoEntity user = new UserInfoEntity();
//        try {
//            imageList = DatabaseHelper.getInstance().getImageInfoDao().queryForFieldValuesArgs(map);
//
//            for(int i=0;i < imageList.size();i++){
//                imageList.get(i).getFilePath();
//            }
////			if(list.size() == 1){
////				user = list.get(0);
////				if(user.getImg() < 0){
////					user.setImg(R.drawable.new_default);
////				}
////			}
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return imageList;
//    }
}
