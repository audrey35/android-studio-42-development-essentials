package com.ebookfrenzy.primarydetailflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ebookfrenzy.primarydetailflow.placeholder.PlaceholderContent;
import com.ebookfrenzy.primarydetailflow.databinding.FragmentWebsiteDetailBinding;

/**
 * A fragment representing a single Website detail screen.
 * This fragment is either contained in a {@link WebsiteListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class WebsiteDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WebsiteDetailFragment() {
    }

private FragmentWebsiteDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlaceholderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      binding = FragmentWebsiteDetailBinding.inflate(inflater, container, false);
      View rootView = binding.getRoot();

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        if (mItem != null) {
            binding.websiteDetail.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(
                            view, request);
                }
            });
            binding.websiteDetail.loadUrl(mItem.website_url);
        }

        return rootView;
    }
@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}