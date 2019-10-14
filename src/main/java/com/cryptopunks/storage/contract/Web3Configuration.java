package com.cryptopunks.storage.contract;

import com.cryptopunks.storage.contract.gateway.CryptoPunksMarket;
import com.cryptopunks.storage.contract.gateway.CryptoPunksMarketGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class Web3Configuration {

    @Bean
    public CryptoPunksMarketGateway cryptoPunksMarketGateway(
            @Value("${cryptopunks.contract.infuraAddress}") String infuraAddress,
            @Value("${cryptopunks.contract.contractAddress}") String contractAddress) {
        Web3j web3 = Web3j.build(new HttpService(infuraAddress));
        TransactionManager txManager = new ReadonlyTransactionManager(web3, contractAddress);
        DefaultGasProvider gasProvider = new DefaultGasProvider();
        return new CryptoPunksMarketGateway(CryptoPunksMarket.load(contractAddress, web3, txManager, gasProvider));
    }

}
