package com.example.test

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
    val students: MutableList<StudentModel>
    ): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        holder.imageEdit.setOnClickListener {
            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.dialog_edit_student)

            val editName = dialog.findViewById<EditText>(R.id.edit_student_name)
            val editId = dialog.findViewById<EditText>(R.id.edit_student_id)
            val buttonSave = dialog.findViewById<Button>(R.id.button_save)
            val buttonCancel = dialog.findViewById<Button>(R.id.button_cancel2)

            editName.setText(student.studentName)
            editId.setText(student.studentId)

            // Lưu thông tin sau khi chỉnh sửa
            buttonSave.setOnClickListener {
                student.studentName= editName.text.toString()
                student.studentId = editId.text.toString()
                notifyItemChanged(position)
                dialog.dismiss()
            }

            buttonCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
        holder.imageRemove.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa ${student.studentName} không?")
                .setPositiveButton("Xóa") { _, _ ->
                    val deletedStudent = student
                    val deletedPosition = position
                    students.removeAt(position)
                    notifyItemRemoved(position)

                    Snackbar.make(holder.itemView, "${student.studentName} đã bị xóa", Snackbar.LENGTH_LONG)
                        .setAction("Hoàn tác") {
                            students.add(deletedPosition, deletedStudent)
                            notifyItemInserted(deletedPosition)
                        }
                        .show()
                }
                .setNegativeButton("Hủy") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

    }
}
