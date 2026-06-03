package com.streamflow.data;

import com.streamflow.model.Comment;
import com.streamflow.model.Movie;
import com.streamflow.model.User;
import com.streamflow.model.VipPlan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockData {

    private static final String[] MOVIE_COVERS = {
        "https://images.unsplash.com/photo-1536440136628-849c177e76a1?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1485846234645-a62644f84728?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1518676590629-3dcbd9c5a5c9?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1478720568477-152d9b164e26?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1594909122849-11daa4e4d2f2?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1585951237318-9ea5e175b891?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1535016120720-40c646be5580?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1616530940355-351fabd9524b?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1626814026160-2237a95fc5a0?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1596727147705-54a9d099308d?w=400&h=600&fit=crop",
        "https://images.unsplash.com/photo-1574375927938-d5a98e8ffe85?w=400&h=600&fit=crop",
    };

    private static final String[] HERO_POSTERS = {
        "https://images.unsplash.com/photo-1626814026160-2237a95fc5a0?w=800&h=450&fit=crop",
        "https://images.unsplash.com/photo-1536440136628-849c177e76a1?w=800&h=450&fit=crop",
        "https://images.unsplash.com/photo-1485846234645-a62644f84728?w=800&h=450&fit=crop",
    };

    public static List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("1", "星际穿越",
            "在未来的地球，农作物因枯萎病而大面积减产，人类面临灭绝危机。前NASA飞行员库珀临危受命，穿越虫洞寻找人类新家园。",
            MOVIE_COVERS[0], HERO_POSTERS[0],
            Arrays.asList("科幻", "冒险"), 2014, "美国", 9.3, true, "169分钟", 1));
        movies.add(new Movie("2", "千与千寻",
            "10岁的少女千寻与父母一起从都市搬家到了乡下。没想到在搬家的途中，一家人发生了意外。",
            MOVIE_COVERS[1], HERO_POSTERS[1],
            Arrays.asList("动画", "奇幻"), 2001, "日本", 9.4, false, "125分钟", 1));
        movies.add(new Movie("3", "盗梦空间",
            "道姆·柯布是一位经验老道的窃贼，他在这一行中算得上是最厉害的，因为他能够潜入人们精神最为脆弱的梦境中，窃取潜意识中有价值的秘密。",
            MOVIE_COVERS[2], HERO_POSTERS[2],
            Arrays.asList("科幻", "悬疑"), 2010, "美国", 9.2, true, "148分钟", 1));
        movies.add(new Movie("4", "霸王别姬",
            "段小楼与程蝶衣是一对打小一起长大的师兄弟，两人一个演生，一个演旦，一向配合天衣无缝。",
            MOVIE_COVERS[3], MOVIE_COVERS[3],
            Arrays.asList("剧情", "爱情"), 1993, "中国", 9.6, false, "171分钟", 1));
        movies.add(new Movie("5", "阿甘正传",
            "阿甘是个智商只有75的低能儿。在学校里为了躲避别的孩子的欺负，听从一个朋友珍妮的话而开始\"跑\"。",
            MOVIE_COVERS[4], MOVIE_COVERS[4],
            Arrays.asList("剧情", "爱情"), 1994, "美国", 9.5, false, "142分钟", 1));
        movies.add(new Movie("6", "肖申克的救赎",
            "20世纪40年代末，小有成就的青年银行家安迪因涉嫌杀害妻子及她的情人而锒铛入狱。",
            MOVIE_COVERS[5], MOVIE_COVERS[5],
            Arrays.asList("剧情", "犯罪"), 1994, "美国", 9.7, true, "142分钟", 1));
        movies.add(new Movie("7", "疯狂动物城",
            "故事发生在一个所有哺乳类动物和谐共存的美好世界中，兔子朱迪从小就梦想着能够成为一名惩恶扬善的刑警。",
            MOVIE_COVERS[6], MOVIE_COVERS[6],
            Arrays.asList("动画", "喜剧"), 2016, "美国", 9.1, false, "108分钟", 1));
        movies.add(new Movie("8", "楚门的世界",
            "楚门是一个标准的中产阶级，他生活在桃源岛，一个风景如画的小城。但他不知道的是，他的一生都在被直播。",
            MOVIE_COVERS[7], MOVIE_COVERS[7],
            Arrays.asList("剧情", "科幻"), 1998, "美国", 9.3, true, "103分钟", 1));
        movies.add(new Movie("9", "你的名字",
            "在远离大都会的小山村，住着巫女世家出身的高中女孩宫水三叶。校园和家庭的原因本就让她充满烦恼。",
            MOVIE_COVERS[8], MOVIE_COVERS[8],
            Arrays.asList("动画", "爱情"), 2016, "日本", 8.9, false, "106分钟", 1));
        movies.add(new Movie("10", "让子弹飞",
            "民国年间，花钱捐得县长的马邦德携妻及随从走马上任。途经南国某地，遭劫匪张麻子一行人伏击。",
            MOVIE_COVERS[9], MOVIE_COVERS[9],
            Arrays.asList("剧情", "喜剧"), 2010, "中国", 9.0, true, "132分钟", 1));
        movies.add(new Movie("11", "黑客帝国",
            "在矩阵中生活的一名年轻的网络黑客尼奥发现，看似正常的现实世界实际上是由一个名为\"矩阵\"的计算机人工智能系统控制的。",
            MOVIE_COVERS[10], MOVIE_COVERS[10],
            Arrays.asList("科幻", "动作"), 1999, "美国", 9.0, true, "136分钟", 1));
        movies.add(new Movie("12", "大话西游",
            "孙悟空护送唐三藏去西天取经路上，与牛魔王合谋欲杀害唐三藏，并偷走了月光宝盒。",
            MOVIE_COVERS[11], MOVIE_COVERS[11],
            Arrays.asList("喜剧", "爱情"), 1995, "中国", 9.2, false, "95分钟", 1));
        return movies;
    }

    public static List<Movie> getHeroMovies() {
        List<Movie> all = getMovies();
        List<Movie> heroes = new ArrayList<>();
        heroes.add(all.get(0));
        heroes.add(all.get(2));
        heroes.add(all.get(5));
        return heroes;
    }

    public static User getCurrentUser() {
        List<Movie> all = getMovies();
        Movie m1 = all.get(0); m1.setProgress(65);
        Movie m2 = all.get(1); m2.setProgress(30);
        Movie m3 = all.get(3); m3.setProgress(88);
        List<Movie> history = Arrays.asList(m1, m2, m3);
        List<Movie> favorites = Arrays.asList(all.get(0), all.get(2));
        List<Movie> downloads = Arrays.asList(all.get(1));
        return new User("u1", "电影爱好者",
            "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200&h=200&fit=crop&crop=face",
            true, "2025-12-31", history, favorites, downloads);
    }

    public static List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("c1", "u2", "影迷小王",
            "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&h=100&fit=crop&crop=face",
            5, "这部电影真的太震撼了！视觉效果和剧情都无可挑剔，强烈推荐大家观看！",
            128, false, "2024-12-15"));
        comments.add(new Comment("c2", "u3", "电影狂人",
            "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop&crop=face",
            4, "整体不错，但是结尾有点仓促，希望能有续集把故事讲完。",
            56, true, "2024-12-14"));
        comments.add(new Comment("c3", "u4", "文艺青年",
            "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop&crop=face",
            5, "每一帧都是壁纸级别的画面，配乐也非常棒，值得反复观看。",
            89, false, "2024-12-13"));
        return comments;
    }

    public static List<VipPlan> getVipPlans() {
        List<VipPlan> plans = new ArrayList<>();
        plans.add(new VipPlan("monthly", "月度会员", 25, 30, "1个月",
            Arrays.asList("1080P高清", "免广告", "抢先观看", "多端同步"), false));
        plans.add(new VipPlan("quarterly", "季度会员", 68, 90, "3个月",
            Arrays.asList("1080P高清", "免广告", "抢先观看", "多端同步", "4K超清", "专属客服"), true));
        plans.add(new VipPlan("yearly", "年度会员", 238, 360, "12个月",
            Arrays.asList("1080P高清", "免广告", "抢先观看", "多端同步", "4K超清", "专属客服", "线下活动", "限量周边"), false));
        return plans;
    }

    public static final List<String> GENRES = Arrays.asList(
        "全部", "动作", "喜剧", "悬疑", "科幻", "爱情", "动画", "剧情", "犯罪");
    public static final List<String> YEARS = Arrays.asList(
        "全部", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016",
        "2015", "2014", "2013", "2012", "2011", "2010", "2000s", "1990s");
    public static final List<String> REGIONS = Arrays.asList(
        "全部", "中国", "美国", "日本", "韩国", "英国", "法国", "印度", "泰国");
}
