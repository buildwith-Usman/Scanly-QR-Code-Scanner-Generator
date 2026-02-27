package com.infinity.interactive.scanqr.generateqr.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.zxing.BarcodeFormat
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.activity.GenerateActivity
import com.infinity.interactive.scanqr.generateqr.adapter.GenerateAdapter
import com.infinity.interactive.scanqr.generateqr.data.preference.GenerateModel
import com.infinity.interactive.scanqr.generateqr.databinding.FragmentGenerateBinding

class QRGenerateFragment : Fragment() {

    var activity: Activity? = null
    var generateModelList: MutableList<GenerateModel>? = null
    var barcodeModelList: MutableList<GenerateModel>? = null
    var generateSocailList: List<Int>? = null
    private var mContext: Context? = null
    private var adapter: GenerateAdapter? = null
    private var barcodeAdapter: GenerateAdapter? = null

    lateinit var qrGenerateFragmentBinding:FragmentGenerateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        qrGenerateFragmentBinding = FragmentGenerateBinding.inflate(inflater, container, false)
        return qrGenerateFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFunctionality()
        initListener()
    }

    private fun initVar() {
        activity = requireActivity()
        mContext = (activity as FragmentActivity).applicationContext
        generateModelList = ArrayList()
        barcodeModelList = ArrayList()
        generateSocailList = ArrayList()
    }

    private fun initFunctionality() {
        val currentFocus = activity!!.currentFocus
        currentFocus?.clearFocus()
        addDataToGenerateAdapter()
        addDataToBarcodeAdapter()
        adapter = GenerateAdapter(mContext!!, generateModelList!!)
        qrGenerateFragmentBinding.generateRecycler.layoutManager = GridLayoutManager(mContext, 4)
        qrGenerateFragmentBinding.generateRecycler.adapter = adapter
        barcodeAdapter = GenerateAdapter(mContext!!, barcodeModelList!!)
        qrGenerateFragmentBinding.barcodeRecycler.layoutManager = GridLayoutManager(mContext, 4)
        qrGenerateFragmentBinding.barcodeRecycler.adapter = barcodeAdapter
    }

    private fun addDataToBarcodeAdapter() {
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon2,
                BarcodeFormat.CODE_128.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon3,
                BarcodeFormat.CODE_39.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon4,
                BarcodeFormat.CODE_93.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon5,
                BarcodeFormat.CODABAR.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon2,
                BarcodeFormat.ITF.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon3,
                BarcodeFormat.EAN_8.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon4,
                BarcodeFormat.EAN_13.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon5,
                BarcodeFormat.UPC_A.toString() + ""
            )
        )
        barcodeModelList!!.add(
            GenerateModel(
                R.drawable.bar_code_icon2,
                BarcodeFormat.UPC_E.toString() + ""
            )
        )
        barcodeModelList!!.add(GenerateModel(R.drawable.bar_code_icon3, "Product"))
        barcodeModelList!!.add(GenerateModel(R.drawable.bar_code_icon4, "ISBN"))
    }

    private fun addDataToGenerateAdapter() {
        generateModelList!!.add(
            GenerateModel(
                R.drawable.text_scanner,
                activity!!.resources.getString(R.string.txt_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.contacts_scanner,
                activity!!.resources.getString(R.string.contact_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.url_scanner,
                activity!!.resources.getString(R.string.url_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.wifi_scanner,
                activity!!.resources.getString(R.string.wifi_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.email_scanner,
                activity!!.resources.getString(R.string.email_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.sms_scanner,
                activity!!.resources.getString(R.string.sms_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.location_scanner,
                activity!!.resources.getString(R.string.location_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.call_scanner,
                activity!!.resources.getString(R.string.call_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.event_scanner,
                activity!!.resources.getString(R.string.event_generate)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.qr_fb_social_icon,
                activity!!.resources.getString(R.string.facebook)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.qr_twitter_social_icon,
                activity!!.resources.getString(R.string.twitter)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.qr_linkedin_social_icon,
                activity!!.resources.getString(R.string.linkdein)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.qr_instagram_social_icon,
                activity!!.resources.getString(R.string.instagram)
            )
        )
        generateModelList!!.add(
            GenerateModel(
                R.drawable.qr_whatsapp_social_icon,
                activity!!.resources.getString(R.string.whatsapp)
            )
        )
    }

    private fun initListener() {

        adapter!!.setClickListener(object : GenerateAdapter.ClickListener {
            override fun onItemClicked(position: Int) {
                val intent = Intent(requireContext(), GenerateActivity::class.java)
                intent.putExtra("qr_code_cat", position)
                startActivity(intent)
            }
        })

        barcodeAdapter!!.setClickListener(object : GenerateAdapter.ClickListener {
            override fun onItemClicked(position: Int) {
                val intent = Intent(getActivity(), GenerateActivity::class.java)
                intent.putExtra("bar_code_cat", position + 2)
                startActivity(intent)
            }
        })

    }

}
