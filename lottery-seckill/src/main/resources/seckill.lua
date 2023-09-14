--参加的活动的id
local activityId = ARGV[1]
--用户id
local userId = ARGV[2]

--获取活动的key
local stockKey = "seckill:stock:" .. activityId
-- 获取已经参与的用户的key
local userKey = "seckill:user:" .. activityId

-- 判断是否还有库存
local stockNum = tonumber(redis.call('get', stockKey))
if stockNum == nil then
    return 1
elseif  stockNum <= 0 then
    return 2
end

-- 判断用户是否已经参与过
if (redis.call('sismember', userKey, userId) == 1) then
    return 3
end

-- 库存减一
redis.call('incrby', stockKey, -1)
-- 添加用户到已经参与的集合中
redis.call('sadd', userKey, userId)
return 0