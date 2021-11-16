package com.example.kotlindemo.jetpack.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlindemo.jetpack.room.dao.BookDao
import com.example.kotlindemo.jetpack.room.dao.UserDao
import com.example.kotlindemo.jetpack.room.entity.Book
import com.example.kotlindemo.jetpack.room.entity.User

@Database(version = 2, entities = [User::class, Book::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookDao(): BookDao

    companion object {


        //数据库升级(新增表)
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table Book (id integer primary key autoincrement not null, name text not null, pages integer not null)")
            }
        }

        //数据升级(更改已有表)
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(" alter table Book add column author text  not null default 'unknown'")
            }
        }

        private var instance: AppDataBase? = null

        @Synchronized
        fun getDataBase(context: Context): AppDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDataBase::class.java, "app_database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build().apply {
                    instance = this
                }
        }
    }
}