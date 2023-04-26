package com.example.polychat24

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){     //UserViewHolder를 RecyclerView.Adapter에 넣음

     //user_layout을 연결(화면 생성)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        //view에 user_layout 넣는다.
        return UserViewHolder(view) //view를 UserViewHolder에 넣어
    }

    //데이터를 전달받아 user_layout에 연결
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = userList[position]    //userList의 데이터를 순서대로 currentUser에 넣음
        holder.nameText.text = currentUser.name //currentUser의 name을 텍스트뷰에 넣음
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {    //RecyclerView.ViewHolder를 상속받아 View를 전달받아 user_layout에 텍스트뷰 객체 생성 가능
        val nameText: TextView = itemView.findViewById(R.id.name_text)  //user_layout의 name_text를 itemView에 넘겨 텍스트뷰 객체 생성함
    }

    //userList의 갯수 가져옴
    override fun getItemCount(): Int {
        return userList.size
    }
}