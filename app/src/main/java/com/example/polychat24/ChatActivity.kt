package com.example.polychat24

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.polychat24.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiveruserID: String

    //바인딩 객체 생성
    private lateinit var binding: ActivityChatBinding

    lateinit var mAuth: FirebaseAuth            //인증 객체
    lateinit var mDbRef: DatabaseReference      //DB 객체

    private lateinit var receiverRoom: String    //수신 대화방
    private lateinit var senderRoom: String     //발신 대회방

    private lateinit var messageList: ArrayList<Message>    //messageList에 Message타입의 데이터가 들어감

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //초기화
        messageList = ArrayList()
        val messageAdapter = MessageAdapter(this, messageList)

        //RycyclerView
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        //UserAdapter의 intent에 담겨 넘어온 데이터를 변수에 담음
        receiverName = intent.getStringExtra("stuName").toString()
        receiveruserID = intent.getStringExtra("userID").toString()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference



        val sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE)
        val senderuserID = sharedPref.getInt("userID", 0).toString()
//        val senderuserID = mAuth.currentUser?.uid   //접속자 userID
        senderRoom = receiveruserID + senderuserID  //발신자방
        receiverRoom = senderuserID + receiveruserID    //수신자방
        supportActionBar?.title = receiverName     //액션바에 상대방 이름 보여주기

        //메시지 전송 버튼 이벤트
        binding.sendBtn.setOnClickListener {
            val message = binding.messageEdit.text.toString()   //입력한 메시지를 message에 담음
            val messageObject = Message(message, senderuserID)  //messageObject에 Message 클래스 형식의 값을 넣음

            //데이터 저장 - chats>senderRoom>messages에 전송된 메시지 저장
            mDbRef.child("chats").child(senderRoom).child("messages").push().setValue(messageObject).addOnSuccessListener {
                //저장 성공시 - chats>receiverRoom>messages에 전송된 메시지 저장(채팅 상대방도 메시지 확인 가능)
                mDbRef.child("chats").child(receiverRoom).child("messages").push().setValue(messageObject)
            }
            binding.messageEdit.setText("") //입력 부분 초기화
        }

        mDbRef.child("chats").child(senderRoom).child("messages").addValueEventListener(object: ValueEventListener{
            //chats>senderRoom>messages 안의 데이터가 변경되면 onDataChange함수 실행
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {     //snapshot = chats>senderRoom>messages 안의 데이터
                messageList.clear() //messageList 비우기

                for(postSnapshot in snapshot.children){     //snapshot의 데이터를 postSnapshot에 담음
                    val message = postSnapshot.getValue(Message::class.java)    //postSnapshot에 담긴 데이터를 message에 담음
                    messageList.add(message!!)  //message를 messageList에 담음
                }
                //적용
                messageAdapter.notifyDataSetChanged()   //화면에 메시지 내용을 보여줌
            }
            //오류 발생시 실행되는 함수
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}