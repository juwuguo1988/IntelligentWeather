package cn.whm.bytes.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by juwuguo on 2020-04-21.
 * 项目采用MVVM架构，
 * Model是数据模型部分
 * View是界面展示部分
 * ViewModel是链接数据模型部分和界面展示部分的桥梁
 * 目的做到业务逻辑和界面展示分离的程序结构设计
 * 如图:
 *                     ***********
 *                     * UI控制区 *
 *                     ***********
 *                          *
 *                          *
 *                       *  *  *
 *                        * * *
 *                          *
 *                    ***************
 *                    * ViewModel层 *
 *                    ***************
 *                          *
 *                          *
 *                       *  *  *
 *                        * * *
 *                          *
 *                    ***************
 *             ****** *    仓库层    * *******
 *             *      ***************        *
 *             *                             *
 *             *                             *
 *             *                             *
 *          *  *  *                       *  *  *
 *           * * *                         * * *
 *             *                             *
 *      ***************              ******************
 *      *   本地数据源 *               *   网络数据源     *
 *      *   （model） *               *   （model）     *
 *      ***************              ******************
 *             *                             *
 *             *                             *
 *             *                             *
 *          *  *  *                       *  *  *
 *           * * *                         * * *
 *             *                             *
 *      ***************              ******************
 *      *   持久化文件 *               *   WebService   *
 *      ***************              ******************
 *
 *说明:UI控制层指向ViewModel层，意思UI控制层会只有ViewModel层的引用，
 * 但反过来，ViewModel层却不能持有UI控制层的引用
 *
 *仓库层的主要工作就是判断调用方请求的数据应该是从本地数据源中获取，
 * 还是从网络数据源中获取，并将获得的数据返回给调用方
 * 因此仓库有点像一个数据获取与缓存的中间层，在本地没有缓存数据的情况下就去网络获取
 * 如果本地已经有缓存了，就直接将缓存数据返回
 */


class SWApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
        const val app_token = "HiB0IK1nwTl44euO"
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

}