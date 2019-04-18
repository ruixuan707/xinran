//
            1. post 新增  Mapping
            2. put  修改
            3. get  查询
            4. delete 删除

自定义查询条件
if (roomInfoPage.getHoldSize() != null) {
    predicateList.add(criteriaBuilder.equal(
            root.get("holdSize").as(Integer.class),
            roomInfoPage.getHoldSize()));
}
get("holdSize") 为实体类中的字段key
roomInfoPage.getHoldSize() 有前端传递过来 传递的key值为holdSize