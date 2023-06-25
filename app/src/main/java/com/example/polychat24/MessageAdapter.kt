package com.example.polychat24

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//MessageAdapter 생성시 context와 messageList 받음
class MessageAdapter(private val context: Context, private val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){    //2개의 ViewHolder(SendViewHolder, ReceiveViewHolder)를 소화하기 위해 RecyclerView.ViewHolder 사용

    private val receive = 1 //수신 ViewHolderType
    private val send = 2 //송신 ViewHolderType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){   //수신 화면
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            ReceiveViewHolder(view)
        }else{  //송신 화면
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            SendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]   //현재 메시지

        //보내는 데이터
        if(holder.javaClass == SendViewHolder::class.java){     //holder값을 SendViewHolder타입으로 변경
            val viewHolder = holder as SendViewHolder   //변경된 값을 viewHolder에 담음
            viewHolder.sendMessage.text = currentMessage.message    //viewHolder로 sendMessage(TextView)에 접근해서 currentMessage.message를 담음
        }else{      //받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size     //messageList 크기 반환
    }

    override fun getItemViewType(position: Int): Int {  //어떤 ViewHolder 사용할지 선택
        val currentMessage = messageList[position]  //메시지 값을 currentMessage에 담음
        val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val currentUserID = sharedPref.getInt("userID", 0).toString()

        return if(currentUserID == currentMessage.sendID){
//        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendID)){               ///
            send    //onCreateViewHolder의 viewType으로 보냄
        }else{
            receive
        }
    }

    //onCreateViewHolder에서 송신측 View를 전달받아 객체 생성
    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sendMessage: TextView = itemView.findViewById(R.id.send_message_text)
    }
    //onCreateViewHolder에서 수신측 View를 전달받아 객체 생성
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.receive_message_text)
    }
}