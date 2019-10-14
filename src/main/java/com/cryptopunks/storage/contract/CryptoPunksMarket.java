package com.cryptopunks.storage.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class CryptoPunksMarket extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_PUNKSOFFEREDFORSALE = "punksOfferedForSale";

    public static final String FUNC_ENTERBIDFORPUNK = "enterBidForPunk";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_ACCEPTBIDFORPUNK = "acceptBidForPunk";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_SETINITIALOWNERS = "setInitialOwners";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_IMAGEHASH = "imageHash";

    public static final String FUNC_NEXTPUNKINDEXTOASSIGN = "nextPunkIndexToAssign";

    public static final String FUNC_PUNKINDEXTOADDRESS = "punkIndexToAddress";

    public static final String FUNC_STANDARD = "standard";

    public static final String FUNC_PUNKBIDS = "punkBids";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_ALLINITIALOWNERSASSIGNED = "allInitialOwnersAssigned";

    public static final String FUNC_ALLPUNKSASSIGNED = "allPunksAssigned";

    public static final String FUNC_BUYPUNK = "buyPunk";

    public static final String FUNC_TRANSFERPUNK = "transferPunk";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_WITHDRAWBIDFORPUNK = "withdrawBidForPunk";

    public static final String FUNC_SETINITIALOWNER = "setInitialOwner";

    public static final String FUNC_OFFERPUNKFORSALETOADDRESS = "offerPunkForSaleToAddress";

    public static final String FUNC_PUNKSREMAININGTOASSIGN = "punksRemainingToAssign";

    public static final String FUNC_OFFERPUNKFORSALE = "offerPunkForSale";

    public static final String FUNC_GETPUNK = "getPunk";

    public static final String FUNC_PENDINGWITHDRAWALS = "pendingWithdrawals";

    public static final String FUNC_PUNKNOLONGERFORSALE = "punkNoLongerForSale";

    public static final Event ASSIGN_EVENT = new Event("Assign", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PUNKTRANSFER_EVENT = new Event("PunkTransfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PUNKOFFERED_EVENT = new Event("PunkOffered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PUNKBIDENTERED_EVENT = new Event("PunkBidEntered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PUNKBIDWITHDRAWN_EVENT = new Event("PunkBidWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PUNKBOUGHT_EVENT = new Event("PunkBought", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PUNKNOLONGERFORSALE_EVENT = new Event("PunkNoLongerForSale", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected CryptoPunksMarket(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CryptoPunksMarket(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CryptoPunksMarket(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CryptoPunksMarket(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple5<Boolean, BigInteger, String, BigInteger, String>> punksOfferedForSale(BigInteger param0) {
        final Function function = new Function(FUNC_PUNKSOFFEREDFORSALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple5<Boolean, BigInteger, String, BigInteger, String>>(function,
                new Callable<Tuple5<Boolean, BigInteger, String, BigInteger, String>>() {
                    @Override
                    public Tuple5<Boolean, BigInteger, String, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<Boolean, BigInteger, String, BigInteger, String>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> enterBidForPunk(BigInteger punkIndex, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ENTERBIDFORPUNK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptBidForPunk(BigInteger punkIndex, BigInteger minPrice) {
        final Function function = new Function(
                FUNC_ACCEPTBIDFORPUNK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(minPrice)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setInitialOwners(List<String> addresses, List<BigInteger> indices) {
        final Function function = new Function(
                FUNC_SETINITIALOWNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(indices, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> imageHash() {
        final Function function = new Function(FUNC_IMAGEHASH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> nextPunkIndexToAssign() {
        final Function function = new Function(FUNC_NEXTPUNKINDEXTOASSIGN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> punkIndexToAddress(BigInteger param0) {
        final Function function = new Function(FUNC_PUNKINDEXTOADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> standard() {
        final Function function = new Function(FUNC_STANDARD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<Boolean, BigInteger, String, BigInteger>> punkBids(BigInteger param0) {
        final Function function = new Function(FUNC_PUNKBIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<Boolean, BigInteger, String, BigInteger>>(function,
                new Callable<Tuple4<Boolean, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple4<Boolean, BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<Boolean, BigInteger, String, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String param0) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> allInitialOwnersAssigned() {
        final Function function = new Function(
                FUNC_ALLINITIALOWNERSASSIGNED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> allPunksAssigned() {
        final Function function = new Function(FUNC_ALLPUNKSASSIGNED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> buyPunk(BigInteger punkIndex, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYPUNK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> transferPunk(String to, BigInteger punkIndex) {
        final Function function = new Function(
                FUNC_TRANSFERPUNK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawBidForPunk(BigInteger punkIndex) {
        final Function function = new Function(
                FUNC_WITHDRAWBIDFORPUNK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setInitialOwner(String to, BigInteger punkIndex) {
        final Function function = new Function(
                FUNC_SETINITIALOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> offerPunkForSaleToAddress(BigInteger punkIndex, BigInteger minSalePriceInWei, String toAddress) {
        final Function function = new Function(
                FUNC_OFFERPUNKFORSALETOADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(minSalePriceInWei), 
                new org.web3j.abi.datatypes.Address(160, toAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> punksRemainingToAssign() {
        final Function function = new Function(FUNC_PUNKSREMAININGTOASSIGN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> offerPunkForSale(BigInteger punkIndex, BigInteger minSalePriceInWei) {
        final Function function = new Function(
                FUNC_OFFERPUNKFORSALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(minSalePriceInWei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> getPunk(BigInteger punkIndex) {
        final Function function = new Function(
                FUNC_GETPUNK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> pendingWithdrawals(String param0) {
        final Function function = new Function(FUNC_PENDINGWITHDRAWALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> punkNoLongerForSale(BigInteger punkIndex) {
        final Function function = new Function(
                FUNC_PUNKNOLONGERFORSALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(punkIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<AssignEventResponse> getAssignEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ASSIGN_EVENT, transactionReceipt);
        ArrayList<AssignEventResponse> responses = new ArrayList<AssignEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AssignEventResponse typedResponse = new AssignEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.punkIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AssignEventResponse> assignEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AssignEventResponse>() {
            @Override
            public AssignEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ASSIGN_EVENT, log);
                AssignEventResponse typedResponse = new AssignEventResponse();
                typedResponse.log = log;
                typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.punkIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AssignEventResponse> assignEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ASSIGN_EVENT));
        return assignEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public List<PunkTransferEventResponse> getPunkTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUNKTRANSFER_EVENT, transactionReceipt);
        ArrayList<PunkTransferEventResponse> responses = new ArrayList<PunkTransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PunkTransferEventResponse typedResponse = new PunkTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.punkIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PunkTransferEventResponse> punkTransferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PunkTransferEventResponse>() {
            @Override
            public PunkTransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUNKTRANSFER_EVENT, log);
                PunkTransferEventResponse typedResponse = new PunkTransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.punkIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PunkTransferEventResponse> punkTransferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUNKTRANSFER_EVENT));
        return punkTransferEventFlowable(filter);
    }

    public List<PunkOfferedEventResponse> getPunkOfferedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUNKOFFERED_EVENT, transactionReceipt);
        ArrayList<PunkOfferedEventResponse> responses = new ArrayList<PunkOfferedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PunkOfferedEventResponse typedResponse = new PunkOfferedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.toAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.minValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PunkOfferedEventResponse> punkOfferedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PunkOfferedEventResponse>() {
            @Override
            public PunkOfferedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUNKOFFERED_EVENT, log);
                PunkOfferedEventResponse typedResponse = new PunkOfferedEventResponse();
                typedResponse.log = log;
                typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.toAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.minValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PunkOfferedEventResponse> punkOfferedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUNKOFFERED_EVENT));
        return punkOfferedEventFlowable(filter);
    }

    public List<PunkBidEnteredEventResponse> getPunkBidEnteredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUNKBIDENTERED_EVENT, transactionReceipt);
        ArrayList<PunkBidEnteredEventResponse> responses = new ArrayList<PunkBidEnteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PunkBidEnteredEventResponse typedResponse = new PunkBidEnteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.fromAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PunkBidEnteredEventResponse> punkBidEnteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PunkBidEnteredEventResponse>() {
            @Override
            public PunkBidEnteredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUNKBIDENTERED_EVENT, log);
                PunkBidEnteredEventResponse typedResponse = new PunkBidEnteredEventResponse();
                typedResponse.log = log;
                typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.fromAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PunkBidEnteredEventResponse> punkBidEnteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUNKBIDENTERED_EVENT));
        return punkBidEnteredEventFlowable(filter);
    }

    public List<PunkBidWithdrawnEventResponse> getPunkBidWithdrawnEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUNKBIDWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<PunkBidWithdrawnEventResponse> responses = new ArrayList<PunkBidWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PunkBidWithdrawnEventResponse typedResponse = new PunkBidWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.fromAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PunkBidWithdrawnEventResponse> punkBidWithdrawnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PunkBidWithdrawnEventResponse>() {
            @Override
            public PunkBidWithdrawnEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUNKBIDWITHDRAWN_EVENT, log);
                PunkBidWithdrawnEventResponse typedResponse = new PunkBidWithdrawnEventResponse();
                typedResponse.log = log;
                typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.fromAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PunkBidWithdrawnEventResponse> punkBidWithdrawnEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUNKBIDWITHDRAWN_EVENT));
        return punkBidWithdrawnEventFlowable(filter);
    }

    public List<PunkBoughtEventResponse> getPunkBoughtEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUNKBOUGHT_EVENT, transactionReceipt);
        ArrayList<PunkBoughtEventResponse> responses = new ArrayList<PunkBoughtEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PunkBoughtEventResponse typedResponse = new PunkBoughtEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.fromAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.toAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PunkBoughtEventResponse> punkBoughtEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PunkBoughtEventResponse>() {
            @Override
            public PunkBoughtEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUNKBOUGHT_EVENT, log);
                PunkBoughtEventResponse typedResponse = new PunkBoughtEventResponse();
                typedResponse.log = log;
                typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.fromAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.toAddress = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PunkBoughtEventResponse> punkBoughtEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUNKBOUGHT_EVENT));
        return punkBoughtEventFlowable(filter);
    }

    public List<PunkNoLongerForSaleEventResponse> getPunkNoLongerForSaleEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUNKNOLONGERFORSALE_EVENT, transactionReceipt);
        ArrayList<PunkNoLongerForSaleEventResponse> responses = new ArrayList<PunkNoLongerForSaleEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PunkNoLongerForSaleEventResponse typedResponse = new PunkNoLongerForSaleEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PunkNoLongerForSaleEventResponse> punkNoLongerForSaleEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PunkNoLongerForSaleEventResponse>() {
            @Override
            public PunkNoLongerForSaleEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUNKNOLONGERFORSALE_EVENT, log);
                PunkNoLongerForSaleEventResponse typedResponse = new PunkNoLongerForSaleEventResponse();
                typedResponse.log = log;
                typedResponse.punkIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PunkNoLongerForSaleEventResponse> punkNoLongerForSaleEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUNKNOLONGERFORSALE_EVENT));
        return punkNoLongerForSaleEventFlowable(filter);
    }

    @Deprecated
    public static CryptoPunksMarket load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CryptoPunksMarket(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CryptoPunksMarket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CryptoPunksMarket(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CryptoPunksMarket load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CryptoPunksMarket(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CryptoPunksMarket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CryptoPunksMarket(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class AssignEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger punkIndex;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class PunkTransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger punkIndex;
    }

    public static class PunkOfferedEventResponse extends BaseEventResponse {
        public BigInteger punkIndex;

        public String toAddress;

        public BigInteger minValue;
    }

    public static class PunkBidEnteredEventResponse extends BaseEventResponse {
        public BigInteger punkIndex;

        public String fromAddress;

        public BigInteger value;
    }

    public static class PunkBidWithdrawnEventResponse extends BaseEventResponse {
        public BigInteger punkIndex;

        public String fromAddress;

        public BigInteger value;
    }

    public static class PunkBoughtEventResponse extends BaseEventResponse {
        public BigInteger punkIndex;

        public String fromAddress;

        public String toAddress;

        public BigInteger value;
    }

    public static class PunkNoLongerForSaleEventResponse extends BaseEventResponse {
        public BigInteger punkIndex;
    }
}
