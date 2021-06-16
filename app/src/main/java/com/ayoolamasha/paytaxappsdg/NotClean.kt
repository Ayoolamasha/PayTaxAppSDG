package com.ayoolamasha.paytaxappsdg
//SIGN UP STUFF

//class SignUpFragment : Fragment() {
//
//    private lateinit var signUpViewModel:SignUpViewModel
//
//    private lateinit var dialogBuilder: AlertDialog.Builder
//    private lateinit var dialog: AlertDialog
//
//    private lateinit var clipboardManager: ClipboardManager
//    //private lateinit var clipData: ClipData
//
//    private lateinit var taxId: TextView
//    private lateinit var copy: TextView
//    private lateinit var login: TextView
//
//
//    companion object {
//        fun newInstance() = SignUpFragment()
//    }
//
//    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
//
//    private lateinit var viewModel: SignUpViewModel
//
//    private lateinit var firstName: TextInputEditText
//    private lateinit var lastName: TextInputEditText
//    private lateinit var phone: TextInputEditText
//    private lateinit var email: TextInputEditText
//    private lateinit var password: TextInputEditText
//    private lateinit var createButton: CircularProgressButton
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        signUpViewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application)).get(
//            SignUpViewModel::class.java
//        )
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
//
//
//        init(view)
//
//        view.findViewById<Button>(R.id.closeButton).setOnClickListener {
//            view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
//
//
//        }
//
//        view.findViewById<Button>(R.id.backToLoginUp).setOnClickListener {
//            view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
//        }
//
//        createButton.setOnClickListener {
//            signUpRequest()
//            createButton.startAnimation()
//
//        }
//
//
//        signUpViewModel.mLiveSignUpResponse.observe(requireActivity(), { type ->
//            run {
//                coroutineScope.launch {
//                    when (type) {
//                        is ApiResult.Success -> {
//                            createButton.dispose()
//                            val success = type.response as SignUpResponse
//                            Timber.tag("success").d(success.signUpResponseData?.taxPayerId)
//                            Timber.tag("success").d(success.accessToken)
//                            //Timber.tag("success").d(success.status)
//
//                            coroutineScope.launch(Dispatchers.Main) {
//                                dialogBuilder = AlertDialog.Builder(activity)
//
//                                val view1: View? = layoutInflater.inflate(
//                                    R.layout.layout_sign_up_dialog,
//                                    container,
//                                    false
//                                )
//
//                                if (view1 != null) {
//                                    taxId = view1.findViewById(R.id.dynamicPayerID)
//                                    copy = view1.findViewById(R.id.copyStatic)
//                                    login = view1.findViewById(R.id.navToLogin)
//
//                                }else{
//                                    Toast.makeText(
//                                        activity,
//                                        success.signUpResponseData?.taxPayerId,
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//
//                                dialogBuilder.setView(view1);
//                                dialogBuilder.setCancelable(false);
//                                dialog = dialogBuilder.create();
//                                dialog.show()
//
//                                taxId.text = success.signUpResponseData?.taxPayerId
//
//                                copy.setOnClickListener {
//                                    val taxCode: String = success.signUpResponseData?.taxPayerId?: return@setOnClickListener
//                                    val clipData:ClipData = ClipData.newPlainText("text", taxCode)
//                                    clipboardManager.setPrimaryClip(clipData)
//
//                                    login.visibility=View.VISIBLE
//                                }
//
//                                login.setOnClickListener {
//                                    view1?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)
//                                    dialog.dismiss()
//
//                                }
//
//
//
//                            }
//                            //view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                        }
//                        is ApiResult.NetworkError -> {
//                            createButton.dispose()
//                            Snackbar.make(
//                                view,
//                                "Check your Network Connection",
//                                Snackbar.LENGTH_SHORT
//                            ).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
//                        }
//                        is ApiResult.Failure -> {
//                            createButton.dispose()
//                            val error: SignUpErrorPojo = type as SignUpErrorPojo
//                            Timber.tag("Failure").d(error.message)
//                            Snackbar.make(
//                                view,
//                                error.message!!,
//                                Snackbar.LENGTH_SHORT
//                            ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
//                        }
//                        is ApiResult.Exception -> {
//                            createButton.dispose()
//                            Timber.tag("Exception").d(type.t)
//                            type.t.message?.let {
//                                Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
//                                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
//                            }
//                        }
//                        else -> ApiResult.NetworkError(true)
//                    }
//                }
//            }
//
//        })
//
//        return view
//    }
//
////    override fun onActivityCreated(savedInstanceState: Bundle?) {
////        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
////        // TODO: Use the ViewModel
////    }
//
//
//    private fun init(view: View){
//        firstName = view.findViewById(R.id.inputFirstName)
//        lastName = view.findViewById(R.id.inputLastName)
//        phone = view.findViewById(R.id.inputPhoneNumber)
//        email = view.findViewById(R.id.inputEmail)
//        password = view.findViewById(R.id.inputPassword)
//        createButton = view.findViewById(R.id.createAccount)
//        clipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//    }
//
//    private fun signUpRequest(){
//        val userFirstName = firstName.text.toString().trim()
//        val userLastName = lastName.text.toString().trim()
//        val userPhone = phone.text.toString().trim()
//        val userEmail = email.text.toString().trim()
//        val userPassword = password.text.toString().trim()
//
//        if (userFirstName.isEmpty() || userLastName.isEmpty() || userPassword.isEmpty()
//            || userEmail.isEmpty() || userPhone.isEmpty()){
//
//            Toast.makeText(activity, "Fill All Fields", Toast.LENGTH_SHORT).show()
//        }else{
//
//            signUpViewModel.userSignUp(
//                userFirstName,
//                userLastName,
//                userEmail,
//                userPhone,
//                userPassword
//            )
//
//        }
//
//
//    }
//
//}


// LOGIN STUFF

//lateinit var userDataRepository: UserDataRepository


//private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)





//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application))
//            .get(LoginViewModel::class.java)
//
//    }


//        viewModel.mLiveLoginResponse.observe(requireActivity(), { type ->
//            run {
//                coroutineScope.launch {
//                    when (type) {
//                        is ApiResult.Success -> {
//                            val success = type.response as LoginRequestResponse
//                            //Timber.tag("success").d(success.loginResponseData.taxPayerId)
//                            val fullName:String = success.loginResponseData.firstName + success.loginResponseData.lastName
//                            val bundle = bundleOf(
//                                "taxId" to success.loginResponseData.taxPayerId,
//                                "accessToken" to success.accessToken,
//                                "name" to fullName,
//                                "userEmail" to success.loginResponseData.email
//                            )
////                            bundle.putString("userTaxId", success.loginResponseData.taxPayerId)
////                            bundle.putString("userAccessToken", success.accessToken)
////                            bundle.putString("userName", fullName)
//
////                            val userDataPojo = UserDataPojo(success.accessToken,
////                                success.loginResponseData._id, success.loginResponseData.firstName,
////                                success.loginResponseData.lastName, success.loginResponseData.email,
////                            success.loginResponseData.phone, success.loginResponseData.password, success.loginResponseData.taxPayerId)
////                            viewModel.saveUserViewModel(userDataPojo)
//                            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
//                        }
//                        is ApiResult.NetworkError -> {
//                            login.dispose()
//                            Snackbar.make(
//                                view,
//                                "Check your Network Connection",
//                                Snackbar.LENGTH_SHORT
//                            ).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
//                        }
//                        is ApiResult.Failure -> {
//                            login.dispose()
//                            val error: SignUpErrorPojo = type as SignUpErrorPojo
//                            Timber.tag("Failure").d(error.message)
//                            Snackbar.make(
//                                view,
//                                error.message!!,
//                                Snackbar.LENGTH_SHORT
//                            ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
//                        }
//                        is ApiResult.Exception -> {
//                            login.dispose()
//                            Timber.tag("Exception").d(type.t)
//                            type.t.message?.let {
//                                Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
//                                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
//                            }
//                        }
//                        else -> ApiResult.NetworkError(true)
//                    }
//                }
//            }
//
//        })

// LOGIN VIEW MODEL

//class LoginViewModel(application: Application) : AndroidViewModel(application) {
//
//    /**
//     * This is the job for all coroutines started by this ViewModel.
//     *
//     * Cancelling this job will cancel all coroutines started by this ViewModel.
//     */
//    private val viewModelJob = SupervisorJob()
//
//    /**
//     * This is the main scope for all coroutines launched by MainViewModel.
//     *
//     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
//     * viewModelJob.cancel()
//     */
//    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//
//    private lateinit var loginRepository: LoginRepository
//   // private lateinit var userDataRepository: UserDataRepository
//
//    //val allUserDataPojo: LiveData<List<UserDataPojo>> = repository.allUserData.asLiveData()
//
////    fun saveUserDataViewModel(userDataPojo: UserDataPojo) = viewModelScope.launch {
////        repository.saveUserDataRepository(userDataPojo)
////
////    }
//
//
//    var mLiveLoginResponse: LiveData<ApiResult<Any>>
//    private var _mMutableLoginResponse: MutableLiveData<ApiResult<Any>>
//
//    init {
//        loginRepository = LoginRepository.getInstance(application)
//        _mMutableLoginResponse = MutableLiveData()
//        mLiveLoginResponse = _mMutableLoginResponse
//        //userDataRepository = UserDataRepository()
//
//    }
//
//
//    fun userLogin(userTaxId: String, userPassword:String){
//        viewModelScope.launch {
//            val loginRequest = LoginRequest(userTaxId, userPassword)
//            val loginResult = loginRepository.makeLoginRequest(loginRequest)
//            _mMutableLoginResponse.postValue(loginResult)
//        }
//    }
//
////    fun saveUserViewModel(userDataPojo: UserDataPojo){
////        userDataRepository.saveUserDataRepository(userDataPojo)
////    }
//
////
////        class LoginViewModelFactory(private val application: UserDataRepository, private val repository: UserDataRepository) : ViewModelProvider.Factory {
////        override fun <T : ViewModel> create(modelClass: Class<T>): T {
////            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
////                @Suppress("UNCHECKED_CAST")
////                return LoginViewModel(application, repository) as T
////            }
////            throw IllegalArgumentException("Unknown ViewModel class")
////        }
////}
//
//
//    /**
//     * Cancel all coroutines when the ViewModel is cleared
//     */
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
//}


// ROOM DATA BASE
//    companion object {
//        @Volatile
//        private var INSTANCE: UserDataRoomDatabase? = null
//        private const val DATABASE_NAME:String = "user_data"
//
//        fun getDatabase(
//            context: Context,
//            //scope: CoroutineScope
//        ): UserDataRoomDatabase {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDataRoomDatabase::class.java,
//                    DATABASE_NAME
//                )
//                    // Wipes and rebuilds instead of migrating if no Migration object.
//                    // Migration is not part of this code lab.
//                    .fallbackToDestructiveMigration()
//                    .addCallback(UserDatabaseCallback())
//                    .build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
//
//        private class UserDatabaseCallback(
//            //private val scope: CoroutineScope
//        ) : RoomDatabase.Callback() {
//            /**
//             * Override the onCreate method to populate the database.
//             */
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                // If you want to keep the data through app restarts,
//                // comment out the following line.
////                INSTANCE?.let { database ->
////                    scope.launch(Dispatchers.IO) {
////                        populateDatabase(database.userDao())
////                    }
////                }
//            }
//        }
//
//        /**
//         * Populate the database in a new coroutine.
//         * If you want to start with more words, just add them.
//         */
////        suspend fun populateDatabase(userDao: UserDao) {
////            // Start the app with a clean database every time.
////            // Not needed if you only populate on creation.
////            userDao.deleteAll()
////
////            var word = Word("Hello")
////            wordDao.insert(word)
////            word = Word("World!")
////            wordDao.insert(word)
////        }
//    }

//abstract class UserDataRoomDatabase: RoomDatabase() {
//
//    abstract fun userDao(): UserDao
//
//    companion object{
//        @Volatile
//        private var instance: UserDataRoomDatabase? = null
//
//        private const val DATABASE_NAME:String = "user_data"
//
////        fun getDatabase(context: Context, scope: CoroutineScope): UserDataRoomDatabase{
////            return INSTANCE ?: synchronized(this){
////                val instance = Room.databaseBuilder(context.applicationContext,
////                    UserDataRoomDatabase::class.java, DATABASE_NAME)
////                    .fallbackToDestructiveMigration()
////                    .addCallback(UserDatabaseCallBack(scope))
////                    .build()
////                INSTANCE = instance
////                return instance
////            }
////        }
////
////        private class UserDatabaseCallBack(private val scope: CoroutineScope):RoomDatabase.Callback(){
////            override fun onCreate(db: SupportSQLiteDatabase) {
////                super.onCreate(db)
////                INSTANCE?. let {database  -> scope.launch(Dispatchers.IO){
////                    populateDatabase(database.userDao())
////
////                }   }
////            }
////
////        }
////
////        suspend fun populateDatabase(userDao: UserDao){
////            val userData = UserDataPojo("2","Jane", "Doe",
////                "janedoe@mail.com", "0800523456", "1234567#", "taxId123",
////                "ID")
////            userDao.saveUserData(userData)
////        }
//
//
//
//
//        //private const val DATABASE_NAME:String = "user_data"
//        private const val NUMBER_OF_THREAD:Int = 1
//        val databaseWriterExecutor:ExecutorService = Executors.newFixedThreadPool(
//            NUMBER_OF_THREAD)
//
//        @Synchronized
//        fun getInstance(context: Context): UserDataRoomDatabase? {
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDataRoomDatabase::class.java, DATABASE_NAME
//                )
//                    .fallbackToDestructiveMigration()
//                    .addCallback(callback)
//                    .build()
//            }
//            return instance
//        }
//
//        private val callback: Callback = object : Callback() {
//            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//
//            }
//        }
//    }
//
//
//}

// SIGN UP VIEW MODEL

//class SignUpViewModel(application: Application) : AndroidViewModel(application){
//
//    /**
//     * This is the job for all coroutines started by this ViewModel.
//     *
//     * Cancelling this job will cancel all coroutines started by this ViewModel.
//     */
//    private val viewModelJob = SupervisorJob()
//
//    /**
//     * This is the main scope for all coroutines launched by MainViewModel.
//     *
//     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
//     * viewModelJob.cancel()
//     */
//    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//
//
//    private lateinit var signUpRepository: SignUpRepository
//
//    var mLiveSignUpResponse: LiveData<ApiResult<Any>>
//
//    private val mMutableSignUpResponse: MutableLiveData<ApiResult<Any>>
//
//    init {
//        signUpRepository = SignUpRepository.getInstance(application)
//        mMutableSignUpResponse = MutableLiveData()
//        mLiveSignUpResponse = mMutableSignUpResponse
//    }
//
//    fun userSignUp(userFirstName: String, userLastName: String, userEmail: String,
//    userPhone: String, userPassword: String){
//        viewModelScope.launch {
//            val signUpRequest = SignUpRequest(userFirstName, userLastName, userEmail, userPhone, userPassword)
//            val signUpResult = signUpRepository.makeSignUpRequest(signUpRequest)
//            mMutableSignUpResponse.postValue(signUpResult)
//
//        }
//    }
//
//
//    /**
//     * Cancel all coroutines when the ViewModel is cleared
//     */
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
//
//
//
//
//}

// SIGN UP REPOSITORY

//class SignUpRepository private constructor(application: Application) {
//
//    private var context: Context
//    private var apiServicesInterface: ApiServicesInterface
//    private lateinit var modelSuccess: ApiResult.Success<SignUpResponse>
//    private lateinit var modelError: ApiResult.Error<SignUpError>
//    private val coroutineContext: CoroutineContext get() = Dispatchers.IO
//
//
//    init {
//        this.context = application.applicationContext
//        apiServicesInterface = ApiServiceBuilder.buildService(ApiServicesInterface::class.java)
//    }
//
//    companion object :
//        SingletonHolder<SignUpRepository, Application>(::SignUpRepository)
//
//
//    suspend fun makeSignUpRequest(signUpRequest: SignUpRequest): ApiResult<Any> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = apiServicesInterface.signUp(signUpRequest)
//                if (response.isSuccessful) {
//                    if (response.body() != null && response.code() == 200) {
//                        val signUpResponse = response.body()
//                        return@withContext ApiResult.Success(signUpResponse)
//                    } else if (response.code() != 200) {
//                        return@withContext ApiResult.Error(response.body())
//                    } else {
//                        return@withContext ApiResult.NetworkError(true)
//                    }
//                } else {
//                    return@withContext ApiResult.Error(response.errorBody())
//
//                }
//
//            } catch (t: Throwable) {
//                if (t !is CancellationException) {
//                    return@withContext ApiResult.Exception(t)
//                } else {
//                    throw t
//                }
//            }
//        }
//
//
//    }
//}

// HOME VIEW MODEL

//class HomeViewModel(application: Application) : AndroidViewModel(application){
//
//    /**
//     * This is the job for all coroutines started by this ViewModel.
//     *
//     * Cancelling this job will cancel all coroutines started by this ViewModel.
//     */
//    private val viewModelJob = SupervisorJob()
//
//    /**
//     * This is the main scope for all coroutines launched by MainViewModel.
//     *
//     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
//     * viewModelJob.cancel()
//     */
//    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//    private var homeRepository: HomeRepository
//    var mLiveGetTaxTypeResponse: LiveData<ApiResult<Any>>
//    private var _mMutableGetTaxTypeResponse: MutableLiveData<ApiResult<Any>>
//
//    init {
//        homeRepository = HomeRepository.getInstance(application)
//        _mMutableGetTaxTypeResponse = MutableLiveData()
//        mLiveGetTaxTypeResponse = _mMutableGetTaxTypeResponse
//
//
//    }
//
//    fun getTaxTypeViewModel(){
//        viewModelScope.launch {
//            val getTypeResult = homeRepository.getTaxType()
//            _mMutableGetTaxTypeResponse.postValue(getTypeResult)
//        }
//    }
//
//    fun makeCalculationsViewModel(userTaxId:String, userIncome: String){
//        viewModelScope.launch {
//            val getPost = CalculateTaxRequest(userTaxId, userIncome)
//            val getCalculations = homeRepository.makeCalculateRequest(getPost)
//            _mMutableGetTaxTypeResponse.postValue(getCalculations)
//        }
//    }
//}



//class LoginRepository private constructor(application: Application){
//
//    private var context: Context
//    private lateinit var apiServicesInterface: ApiServicesInterface
//    private val coroutineContext: CoroutineContext get() = Dispatchers.IO
//
//    companion object :
//        SingletonHolder<LoginRepository, Application>(::LoginRepository)
//
//
//
//    init {
//            this.context = application.applicationContext
//             apiServicesInterface = ApiServiceBuilder.buildService(ApiServicesInterface::class.java)
//    }
//
//    suspend fun makeLoginRequest(loginRequest: LoginRequest): ApiResult<Any>{
//       return withContext(Dispatchers.IO){
//           try {
//
//               val response = apiServicesInterface.login(loginRequest)
//               if (response.isSuccessful){
//                   if (response.body() != null && response.code()==200){
//                       val loginResponse = response.body()
//                       return@withContext ApiResult.Success(loginResponse)
//                   }else if(response.code() != 200){
//                       return@withContext ApiResult.Error(response.message())
//
//                   }else{
//                       return@withContext ApiResult.NetworkError(true)
//                   }
//               }else{
//                   return@withContext ApiResult.Error(response.errorBody())
//               }
//           }catch (t: Throwable) {
//               if (t !is CancellationException) {
//                   return@withContext ApiResult.Exception(t)
//               } else {
//                   throw t
//               }
//           }
//       }
//    }
//}
