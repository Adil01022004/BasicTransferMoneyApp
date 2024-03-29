package com.example.vrum

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

object MyToast {

    fun showWarning(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}


class MainActivity : AppCompatActivity() {
    private lateinit var user1_balance: TextView
    private lateinit var user2_balance: TextView
    private lateinit var name_msg_sender: Spinner
    private lateinit var name_msg_rcp: Spinner
    private lateinit var input_summma: EditText
    private lateinit var transfer_button: TextView
    private lateinit var username1: TextView
    private lateinit var username2: TextView
    private lateinit var id_msg_sender: EditText
    private lateinit var id_msg_rcp: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val reze_id = "45613"
        val makima_id = "54547"
        user1_balance = findViewById(R.id.user1_balance)
        user2_balance = findViewById(R.id.user2_balance)
        name_msg_sender = findViewById(R.id.name_msg_sender)
        name_msg_rcp = findViewById(R.id.name_msg_rcp)
        input_summma = findViewById(R.id.input_summa)
        transfer_button  = findViewById(R.id.transfer_button)
        username1 = findViewById(R.id.username1)
        username2 = findViewById(R.id.username2)

        user1_balance.text = "500000"
        user2_balance.text = "500000"


        //список для Spinner
        val listUsername = listOf("Reze", "Makima")
        //связка Spinner и listUsername
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listUsername)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        name_msg_rcp.adapter = arrayAdapter
        name_msg_sender.adapter = arrayAdapter

        // Установка обработчиков выбора элементов Spinner
        name_msg_sender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                handleTransferButtonClick(parentView.getItemAtPosition(position).toString(), name_msg_rcp.selectedItem.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        name_msg_rcp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                handleTransferButtonClick(name_msg_sender.selectedItem.toString(), parentView.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        transfer_button.setOnClickListener {
            try {
                var user1_balance_string = user1_balance.text.toString()
                var user2_balance_string = user2_balance.text.toString()
                var input_summa_string = input_summma.text.toString()

                var firstUserBalanceInt = user1_balance_string.toInt()
                var secondUserBalanceInt = user2_balance_string.toInt()
                var amountOfBalanceInt = input_summa_string.toInt()

                // Теперь, обработчики выбора элементов уже установлены, и вы можете просто использовать


                if (name_msg_sender.selectedItem.toString() == username1.text.toString() && name_msg_rcp.selectedItem.toString() == username2.text.toString()) {
                    transferMoney(user1_balance, user2_balance, input_summma)
                } else if (name_msg_sender.selectedItem.toString() == username2.text.toString() && name_msg_rcp.selectedItem.toString() == username1.text.toString()) {
                    transferMoney(user2_balance, user1_balance, input_summma)
                }

            } catch (e: Exception) {
                Log.d("Error", "message: ${e.message}")
                // Добавим вывод сообщения об ошибке
                MyToast.showWarning(this, "Ошибка при переводе денег: ${e.message}")
            }


        }



    }

    // метод для обработки нажатия кнопки и передачи данных
    private fun handleTransferButtonClick(firstSelectedValueSpinner: String?, secondSelectedValueSpinner: String?) {
        try {
            if (firstSelectedValueSpinner == username1.text.toString() && secondSelectedValueSpinner == username2.text.toString()) {
                transferMoney(user1_balance, user2_balance, input_summma)
            } else if (firstSelectedValueSpinner == username2.text.toString() && secondSelectedValueSpinner == username1.text.toString()) {
                transferMoney(user2_balance, user1_balance, input_summma)
            }
        } catch (e: Exception) {
            Log.d("Error", "message: ${e.message}")
        }
    }








    fun transferMoney(msgSender: TextView, msgRcp: TextView, amountOfMoney: EditText) {
        try {
            val msgSenderString = msgSender.text.toString()
            val msgRcpString = msgRcp.text.toString()
            val amountOfMoneyString = amountOfMoney.text.toString()

            var msgSenderInt = msgSenderString.toInt()
            var msgRcpInt = msgRcpString.toInt()
            val amountOfMoneyInt = amountOfMoneyString.toInt()

            Log.d("TransferMoney", "Sender: $msgSenderInt, Receiver: $msgRcpInt, Amount: $amountOfMoneyInt")

            if (msgSenderInt < amountOfMoneyInt) {
                MyToast.showWarning(this, "Не хватает денег")
            } else {
                msgSenderInt -= amountOfMoneyInt
                msgRcpInt += amountOfMoneyInt

                msgSender.text = msgSenderInt.toString()
                msgRcp.text = msgRcpInt.toString()
                amountOfMoney.setText("")

                Log.d("TransferMoney", "Transfer successful. Sender: $msgSenderInt, Receiver: $msgRcpInt")
            }
        } catch (e: Exception) {
            Log.d("TransferMoney", "Error transferring money: ${e.message}")
        }
    }
}