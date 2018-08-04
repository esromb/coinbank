package com.example.coinbank;

import com.example.coinbank.service.CoinService;
import com.example.coinbank.dto.CoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

@SpringBootApplication
public class CoinBankApplication implements CommandLineRunner {
	@Autowired
	CoinService coinService;

	public static void main(String[] args) {
		SpringApplication.run(CoinBankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while(true){

			System.out.print("Enter bill to convert to coin: ");
			String billString = scanner.nextLine();
			if (!coinService.isValid(billString)) {
				System.out.println("Please enter correct bill in form 1,2,5,10,20,50,100");
			} else {
				BigDecimal bill = BigDecimal.valueOf(Long.valueOf(billString));
				if (coinService.hasEnough(bill)){
					System.out.println("hasEnough true");
					Map<CoinType, Long> coinTypeCount = new HashMap<>();
					boolean deducted = coinService.deduct(bill, coinTypeCount);
					if (deducted) {
						coinService.print(coinTypeCount);
					}
				} else {
					System.out.println("There is no enough coin");
				}
			}
			if (!coinService.hasEnough(BigDecimal.ONE)){
				System.out.println("No more coin to change");
				exit(0);
			}

		}
	}
}
