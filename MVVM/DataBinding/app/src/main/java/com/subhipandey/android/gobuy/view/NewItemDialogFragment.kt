

package com.subhipandey.android.gobuy.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.subhipandey.android.gobuy.R
import com.subhipandey.android.gobuy.model.GroceryItem
import com.subhipandey.android.gobuy.viewmodel.GroceryListViewModel

class NewItemDialogFragment : DialogFragment() {

  interface NewItemDialogListener {
    fun onDialogPositiveClick(dialog: DialogFragment, item: GroceryItem, isEdit: Boolean,
                              position: Int?)

    fun onDialogNegativeClick(dialog: DialogFragment)
  }

  private var newItemDialogListener: NewItemDialogListener? = null
  private var isEdit = false
  private lateinit var viewModel: GroceryListViewModel

  companion object {
    fun newInstance(title: Int, position: Int?):
        NewItemDialogFragment {

      val newItemDialogFragment = NewItemDialogFragment()
      val args = Bundle()
      args.putInt("dialog_title", title)
      if (position != null) {
        args.putInt("item_position", position)
      } else {
        args.putInt("item_position", -1)
      }
      newItemDialogFragment.arguments = args
      return newItemDialogFragment
    }
  }

  @SuppressLint("InflateParams")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    activity?.let {
      viewModel = ViewModelProviders.of(it).get(GroceryListViewModel::class.java)
    }

    val title = arguments?.getInt("dialog_title")
    val position = arguments?.getInt("item_position", -1)

    val builder = AlertDialog.Builder(activity)
    if (title != null) {
      builder.setTitle(title)
    }

    newItemDialogListener = activity as NewItemDialogListener

    val dialogView =
        activity?.layoutInflater?.inflate(R.layout.dialog_new_item, null)

    val itemNameEditText = dialogView?.findViewById<EditText>(R.id.new_item_edit_text)
    val itemPriceEditText = dialogView?.findViewById<EditText>(R.id.new_item_price)
    val itemAmountEditText = dialogView?.findViewById<EditText>(R.id.new_item_amount)

    if (position != -1) {
      val retrievedItem = viewModel.groceryListItems[position!!]
      isEdit = true
      itemNameEditText?.setText(retrievedItem.itemName)
      itemAmountEditText?.setText(retrievedItem.amount.toString())
      itemPriceEditText?.setText(retrievedItem.price.toString())
    }

    builder.setView(dialogView)
        .setPositiveButton(R.string.save_button) { _, _ ->
          val priceQuantity = itemPriceEditText?.text.toString().toDouble()
          val amountOfItems = itemAmountEditText?.text.toString().toDouble()
          val total: Double = priceQuantity * amountOfItems

          val item = GroceryItem(
              itemName = itemNameEditText?.text.toString(),
              price = priceQuantity,
              amount = amountOfItems.toInt(),
              finalPrice = total)

          newItemDialogListener?.onDialogPositiveClick(this, item, isEdit, position)
        }
        .setNegativeButton(android.R.string.cancel) { _, _ ->
          newItemDialogListener?.onDialogNegativeClick(this)
        }
    return builder.create()
  }
}