package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ratingroom.R

data class TopBarConfig(
    val title: String,
    val showBackButton: Boolean = false,
    val showLogo: Boolean = false,
    val showSaveButton: Boolean = false,
    val showSearchButton: Boolean = false,
    val showProfileMenu: Boolean = false,
    val onBackClick: () -> Unit = {},
    val onSaveClick: () -> Unit = {},
    val onSearchClick: () -> Unit = {},
    val profileMenuExpanded: Boolean = false,
    val onProfileMenuExpandedChange: (Boolean) -> Unit = {},
    val onProfileClick: () -> Unit = {},
    val onFriendsClick: () -> Unit = {},
    val onLogoutClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    config: TopBarConfig
) {
    TopAppBar(
        title = {
            Text(config.title, color = Color.White)
        },
        navigationIcon = {
            when {
                config.showLogo -> {
                    IconButton(onClick = { /* Logo */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.logoratingroom),
                            contentDescription = stringResource(id = R.string.content_desc_logo),
                            tint = Color.Unspecified
                        )
                    }
                }
                config.showBackButton -> {
                    IconButton(onClick = config.onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            tint = Color.White
                        )
                    }
                }
            }
        },
        actions = {
            if (config.showSearchButton) {
                IconButton(
                    onClick = config.onSearchClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                }
            }
            
            if (config.showSaveButton) {
                IconButton(onClick = config.onSaveClick) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(id = R.string.save_button),
                        tint = Color.White
                    )
                }
            }
            
            if (config.showProfileMenu) {
                ProfileMenu(
                    expanded = config.profileMenuExpanded,
                    onExpandedChange = config.onProfileMenuExpandedChange,
                    onProfileClick = config.onProfileClick,
                    onFriendsClick = config.onFriendsClick,
                    onLogoutClick = config.onLogoutClick
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ),
        windowInsets = WindowInsets.Companion.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
    )
}
