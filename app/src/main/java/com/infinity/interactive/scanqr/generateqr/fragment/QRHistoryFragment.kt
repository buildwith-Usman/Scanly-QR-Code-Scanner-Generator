package com.infinity.interactive.scanqr.generateqr.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.activity.ResultActivity
import com.infinity.interactive.scanqr.generateqr.adapter.HistoryAdapter
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.utility.ActivityUtils
import com.infinity.interactive.scanqr.generateqr.utility.DialogUtils
import java.util.Collections

class QRHistoryFragment : Fragment() {

    private var mActivity: Activity? = null
    private var mContext: Context? = null
    private var noResultView: TextView? = null
    private var mRecyclerView: RecyclerView? = null
    var arrayList: ArrayList<String?>? = null
        private set
    private var arrayDateList: ArrayList<String?>? = null
    private var arrayColorList: ArrayList<String?>? = null
    private var adapter: HistoryAdapter? = null
    private var deleteAll: ImageButton? = null
    private var scanned_btn: TextView? = null
    private var created_btn: TextView? = null
    private var current_history_btn = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        initView(rootView)
        initFunctionality()
        initListener()

        //Обновление списка с главного активити
        /*((MainActivity) getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("REFRESHH", Integer.toString(current_history_btn));
                arrayList.clear();
                if(current_history_btn==0) {
                    arrayList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED));
                } else {
                    arrayList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_CREATED));
                }
                Collections.reverse(arrayList);
                refreshList();
            }
        });*/return rootView
    }

    override fun onResume() {
        super.onResume()
        //        mAdManagerInterstitialAd = AdsManagerQ.getInstance().getAd();
//        AdsManagerQ.getInstance().getAd();
//        AdsManagerQ.getInstance().createInterstitialstaticAd(mContext);
    }

    private fun initVar() {
        mActivity = requireActivity()
        mContext = (mActivity as FragmentActivity).getApplicationContext()
    }

    private fun initView(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.recycler_view)
        noResultView = rootView.findViewById(R.id.noResultView)
        deleteAll = rootView.findViewById(R.id.deleteAll)
        scanned_btn = rootView.findViewById(R.id.scanned_res_btn)
        created_btn = rootView.findViewById(R.id.created_res_btn)
    }

    fun initFunctionality() {
        arrayList = ArrayList()
        arrayDateList = ArrayList()
        arrayColorList = ArrayList()
        adapter = HistoryAdapter(mContext, arrayList, arrayDateList)
        mRecyclerView!!.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView!!.adapter = adapter
        arrayList!!.clear()
        arrayList!!.addAll(
            AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED)
        )
        Collections.reverse(arrayList)
        arrayDateList!!.clear()
        arrayDateList!!.addAll(
            AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_SCANNED)
        )
        Collections.reverse(arrayDateList)
        //        arrayColorList.clear();
//        arrayColorList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED));
//        Collections.reverse(arrayColorList);
        refreshList()


//        AdsManagerQ.getInstance().getAd();
//        AdsManagerQ.getInstance().createInterstitialstaticAd(mContext);

//        Log.d("checkAd",mAdManagerInterstitialAd+" ");
    }

    private fun initListener() {
        adapter!!.setClickListener(object : HistoryAdapter.ClickListener {
            override fun onDeleteClicked(position: Int) {
                // AppUtils.copyToClipboard(mContext, arrayList.get(position));
                DialogUtils.showDialogPrompt(
                    mActivity, null, getString(R.string.delete_message_item),
                    getString(R.string.yes), getString(R.string.no), true
                ) { delete(position) }
            }

            override fun onItemClicked(position: Int) {
//                ResultOfTypeAndValue resultValues = AppUtils.getResourceType(arrayList.get(position));
                // TODO Sample fullscreen Ad implementation
                // fullscreen ad show

//                Toast.makeText(mActivity, mAdManagerInterstitialAd+"", Toast.LENGTH_SHORT).show();

//                if(!Constants.removeAds){
//
//                    showHistoryInterstitial(position);
//                }
//                else {
//                    OpenResultFromHistory(position);
//                }
                OpenResultFromHistory(position)
            }

            override fun onItemLongClicked(position: Int) {
                /*DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.delete_message_item),
                        getString(R.string.yes), getString(R.string.no), true, new DialogUtils.DialogActionListener() {
                            @Override
                            public void onPositiveClick() {
                                delete(position);
                            }
                        });*/
            }
        })
        scanned_btn!!.setOnClickListener { //                scanned_btn.setBackgroundColor(getResources().getColor(R.drawable.round_buttons));
//                created_btn.setBackgroundColor(getResources().getColor(R.drawable.default_rounded_buttons));
            scanned_btn!!.setBackgroundResource(R.drawable.round_buttons)
            created_btn!!.setBackgroundResource(R.drawable.history_btn_color)
            scanned_btn!!.setTextColor(resources.getColor(R.color.white))
            created_btn!!.setTextColor(resources.getColor(R.color.tt_black))
            current_history_btn = 0
            arrayList!!.clear()
            arrayList!!.addAll(
                AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED)
            )
            Collections.reverse(arrayList)
            arrayDateList!!.clear()
            arrayDateList!!.addAll(
                AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_SCANNED)
            )
            Collections.reverse(arrayDateList)

//                arrayColorList.clear();
//                arrayColorList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED));
//                Collections.reverse(arrayColorList);
            refreshList()
        }
        created_btn!!.setOnClickListener { //                scanned_btn.setBackgroundColor(getResources().getColor(R.drawable.round_buttons));
//                created_btn.setBackgroundColor(getResources().getColor(R.c));
            scanned_btn!!.setBackgroundResource(R.drawable.history_btn_color)
            created_btn!!.setBackgroundResource(R.drawable.round_buttons)
            scanned_btn!!.setTextColor(resources.getColor(R.color.tt_black))
            created_btn!!.setTextColor(resources.getColor(R.color.white))
            current_history_btn = 1
            arrayList!!.clear()
            arrayList!!.addAll(
                AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_CREATED)
            )
            Collections.reverse(arrayList)
            arrayDateList!!.clear()
            arrayDateList!!.addAll(
                AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_CREATED)
            )
            Collections.reverse(arrayDateList)
            arrayColorList!!.clear()
            arrayColorList!!.addAll(
                AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_CREATED)
            )
            Collections.reverse(arrayColorList)
            refreshList()
        }
        deleteAll!!.setOnClickListener {
            DialogUtils.showDialogPrompt(
                mActivity, null, getString(R.string.delete_message_all),
                getString(R.string.yes), getString(R.string.no), true
            ) { deleteAll() }
        }
    }


    private fun delete(position: Int) {
        if (current_history_btn == 0) {
            AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST_OF_SCANNED, null)
            arrayList!!.removeAt(position)
            Collections.reverse(arrayList)
            AppPreference.getInstance(mContext)
                .setStringArray(PrefKey.RESULT_LIST_OF_SCANNED, arrayList)
            Collections.reverse(arrayList)
            AppPreference.getInstance(mContext).setStringArray(PrefKey.DATE_LIST_OF_SCANNED, null)
            arrayDateList!!.removeAt(position)
            Collections.reverse(arrayDateList)
            AppPreference.getInstance(mContext)
                .setStringArray(PrefKey.DATE_LIST_OF_SCANNED, arrayDateList)

//            AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_SCANNED, null);
//            arrayColorList.remove(position);
//            Collections.reverse(arrayColorList);
//            AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_SCANNED, arrayColorList);
//            Collections.reverse(arrayColorList);
        } else {
            AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST_OF_CREATED, null)
            arrayList!!.removeAt(position)
            Collections.reverse(arrayList)
            AppPreference.getInstance(mContext)
                .setStringArray(PrefKey.RESULT_LIST_OF_CREATED, arrayList)
            Collections.reverse(arrayList)
            AppPreference.getInstance(mContext).setStringArray(PrefKey.DATE_LIST_OF_CREATED, null)
            arrayDateList!!.removeAt(position)
            Collections.reverse(arrayDateList)
            AppPreference.getInstance(mContext)
                .setStringArray(PrefKey.DATE_LIST_OF_CREATED, arrayDateList)

//            AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_CREATED, null);
//            arrayColorList.remove(position);
//            Collections.reverse(arrayColorList);
//            AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_CREATED, arrayColorList);
//            Collections.reverse(arrayColorList);
        }
        Collections.reverse(arrayDateList)
        refreshList()
    }

    private fun deleteAll() {
        if (current_history_btn == 0) {
            AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST_OF_SCANNED, null)
            arrayList!!.clear()
            AppPreference.getInstance(mContext).setStringArray(PrefKey.DATE_LIST_OF_SCANNED, null)
            arrayDateList!!.clear()
            AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_SCANNED, null)
        } else {
            AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST_OF_CREATED, null)
            arrayList!!.clear()
            AppPreference.getInstance(mContext).setStringArray(PrefKey.DATE_LIST_OF_CREATED, null)
            arrayDateList!!.clear()
            AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_CREATED, null)
        }
        arrayColorList!!.clear()
        refreshList()
    }

    fun refreshList() {
        if (arrayList!!.isEmpty()) {
            noResultView!!.visibility = View.VISIBLE
            mRecyclerView!!.visibility = View.GONE
            deleteAll!!.visibility = View.GONE
        } else {
            noResultView!!.visibility = View.GONE
            mRecyclerView!!.visibility = View.VISIBLE
            deleteAll!!.visibility = View.VISIBLE
        }
        adapter!!.notifyDataSetChanged()
    }

    //    @Override
    //    public void setMenuVisibility(final boolean visible) {
    //        super.setMenuVisibility(visible);
    //        if (visible) {
    //            arrayList=new ArrayList<>();
    //            arrayDateList=new ArrayList<>();
    //            arrayColorList=new ArrayList<>();
    //            arrayList.clear();
    //            arrayDateList.clear();
    //            if (current_history_btn == 0) {
    //                arrayList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED));
    //                arrayDateList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_SCANNED));
    //                arrayColorList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED));
    //            } else {
    //                arrayList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_CREATED));
    //                arrayDateList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_CREATED));
    //                arrayColorList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_CREATED));
    //            }
    //            Collections.reverse(arrayList);
    //            Collections.reverse(arrayDateList);
    //            Collections.reverse(arrayColorList);
    //            refreshList();
    //        }
    //    }
    fun OpenResultFromHistory(position: Int) {
        if (current_history_btn == 0) {
            ActivityUtils.getInstance().invokeActivity(
                mActivity,
                ResultActivity::class.java,
                false,
                Constants.HISTORY_SCAN_FRAGMENT,
                position
            )
        } else {
            ActivityUtils.getInstance().invokeActivity(
                mActivity,
                ResultActivity::class.java,
                false,
                Constants.HISTORY_GENERATE_FRAGMENT,
                position
            )
        }
    }
}
